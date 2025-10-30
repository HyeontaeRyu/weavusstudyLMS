package org.example.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.project.model.Exam;
import org.example.project.model.ExamQuestion;
import org.example.project.model.ExamResult;
import org.example.project.model.dto.ExamListDto;
import org.example.project.model.dto.ExamResultDetailDto;
import org.example.project.model.dto.PageRequest;

import java.util.List;

@Mapper
public interface ExamMapper {
    List<ExamListDto> selectAvailableExamsByUserId(@Param("userId") Long userId,
                                                   @Param("page") PageRequest page);

    List<ExamListDto> selectExamsByCourseIdForUser(@Param("courseId") Long courseId,
                                                   @Param("userId") Long userId);

    int countAvailableExamsByUserId(@Param("userId") Long userId);

    Exam findExamById(@Param("id") Long id);

    List<ExamQuestion> findQuestionsByExamId(@Param("examId") Long examId);

    boolean existsExamResult(@Param("userId") Long userId, @Param("examId") Long examId);

    void insertExamResult(ExamResult result);

    void insertExamAnswer(@Param("examResultId") Long examResultId,
                          @Param("questionId") Long questionId,
                          @Param("userAnswer") String userAnswer,
                          @Param("isCorrect") boolean isCorrect);

    void updateExamResultScore(@Param("examResultId") Long examResultId,
                               @Param("score") double score);

    List<ExamQuestion> selectQuestionsByExamId(Long examId);

    List<ExamResultDetailDto> selectExamResultDetail(@Param("userId") Long userId,
                                                     @Param("examId") Long examId);
}