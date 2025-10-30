package org.example.project.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.project.config.security.CustomUserDetails;
import org.example.project.model.Exam;
import org.example.project.model.ExamQuestion;
import org.example.project.model.dto.ExamListDto;
import org.example.project.model.dto.ExamResultDetailDto;
import org.example.project.model.dto.Page;
import org.example.project.service.ExamService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @GetMapping
    public String showAvailableExams(@RequestParam(defaultValue = "1") int page,
                                     Model model, Authentication auth) {
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Long userId = user.getId();

        Page<ExamListDto> examPage = examService.getAvailableExamsForUser(userId, page, 10);
        model.addAttribute("examPage", examPage);
        model.addAttribute("exams", examPage.getContent());

        return "exam-list";
    }

    @GetMapping("/{id}")
    public String showExam(@PathVariable Long id, Model model) {
        Exam exam = examService.findById(id);
        List<ExamQuestion> questions = examService.getQuestions(id);

        ObjectMapper mapper = new ObjectMapper();
        for (ExamQuestion q : questions) {
            if (q.getOptions() != null && !q.getOptions().isEmpty()) {
                try {
                    List<String> opts = mapper.readValue(q.getOptions(), new TypeReference<List<String>>() {
                    });
                    q.setParsedOptions(opts);
                } catch (Exception e) {
                    q.setParsedOptions(List.of());
                }
            } else {
                q.setParsedOptions(List.of());
            }
        }

        model.addAttribute("exam", exam);
        model.addAttribute("questions", questions);
        model.addAttribute("durationMinutes", exam.getDurationMinutes());

        return "exam-take";
    }

    @PostMapping("/{id}/submit")
    @ResponseBody
    public String submitExam(@PathVariable Long id,
                             @RequestParam Map<String, String> answers,
                             Authentication auth) {
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Long userId = user.getId();

        examService.submitExam(userId, id, answers);

        return "<script>alert('提出が完了しました。'); window.opener.location.reload(); window.close();</script>";
    }


    @GetMapping("/{id}/result")
    public String showExamResult(@PathVariable Long id, Authentication auth, Model model) {
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Long userId = user.getId();

        Exam exam = examService.findById(id);
        List<ExamResultDetailDto> resultList = examService.getExamResultDetail(userId, id);

        model.addAttribute("exam", exam);
        model.addAttribute("results", resultList);
        model.addAttribute("userScore", resultList.isEmpty() ? 0 : resultList.get(0).getUserScore());
        model.addAttribute("resultStatus", resultList.isEmpty() ? "N/A" : resultList.get(0).getResultStatus());
        return "exam-result";
    }


}