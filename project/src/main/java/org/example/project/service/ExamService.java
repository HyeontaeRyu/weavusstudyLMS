package org.example.project.service;

import lombok.RequiredArgsConstructor;
import org.example.project.mapper.ExamMapper;
import org.example.project.mapper.ExamResultMapper;
import org.example.project.model.Exam;
import org.example.project.model.ExamQuestion;
import org.example.project.model.ExamResult;
import org.example.project.model.dto.ExamListDto;
import org.example.project.model.dto.ExamResultDetailDto;
import org.example.project.model.dto.Page;
import org.example.project.model.dto.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamMapper examMapper;
    private final ExamResultMapper examResultMapper;

    public Page<ExamListDto> getAvailableExamsForUser(Long userId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        int totalCount = examMapper.countAvailableExamsByUserId(userId);
        List<ExamListDto> exams = examMapper.selectAvailableExamsByUserId(userId, pageRequest);

        return new Page<>(exams, page, size, totalCount);
    }

    public List<ExamListDto> getExamsByCourseIdForUser(Long courseId, Long userId) {
        return examMapper.selectExamsByCourseIdForUser(courseId, userId);
    }


    public Exam findById(Long id) {
        return examMapper.findExamById(id);
    }

    public List<ExamQuestion> getQuestions(Long examId) {
        return examMapper.findQuestionsByExamId(examId);
    }

    @Transactional
    public void submitExam(Long userId, Long examId, Map<String, String> answers) {
        if (examMapper.existsExamResult(userId, examId)) {
            throw new IllegalStateException("この試験はすでに提出されています。");
        }

        ExamResult result = new ExamResult();
        result.setUserId(userId);
        result.setExamId(examId);
        result.setSubmittedAt(LocalDateTime.now());
        examMapper.insertExamResult(result);

        List<ExamQuestion> questions = examMapper.selectQuestionsByExamId(examId);

        double totalScore = 0.0;

        for (ExamQuestion q : questions) {
            String userAnswer = answers.get("answers[" + q.getId() + "]");
            if (userAnswer == null) continue;

            boolean isCorrect = false;

            if (q.getQuestionType().equals("SINGLE") || q.getQuestionType().equals("MULTI")) {
                if (q.getCorrectAnswer() != null &&
                        q.getCorrectAnswer().trim().equals(userAnswer.trim())) {
                    isCorrect = true;
                    totalScore += q.getScore();
                }
            }

            examMapper.insertExamAnswer(result.getId(), q.getId(), userAnswer, isCorrect);
        }

        examMapper.updateExamResultScore(result.getId(), totalScore);
    }

    public List<ExamResultDetailDto> getExamResultDetail(Long userId, Long examId) {
        return examMapper.selectExamResultDetail(userId, examId);
    }

}