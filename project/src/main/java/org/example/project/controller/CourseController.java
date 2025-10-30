package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.project.config.security.CustomUserDetails;
import org.example.project.model.dto.CourseDetailDto;
import org.example.project.model.dto.ExamListDto;
import org.example.project.model.dto.Page;
import org.example.project.model.dto.UserEnrollmentStatus;
import org.example.project.service.CourseService;
import org.example.project.service.EnrollmentService;
import org.example.project.service.ExamService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final ExamService examService;


    @GetMapping
    public String list(Authentication auth,
                       @RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "8") int size,
                       Model model) {
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Page<UserEnrollmentStatus> coursePage = courseService.getPagedCourses(user.getId(), page, size);

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("page", coursePage.getPage());
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("activeTab", "courses");

        return "courses";
    }

    @GetMapping("/{courseId}")
    public String detail(@PathVariable Long courseId, Authentication auth, Model model) {
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        CourseDetailDto course = courseService.getCourseDetail(courseId, user.getId());
        model.addAttribute("course", course);
        model.addAttribute("activeTab", "courses");


        return "course-detail";
    }

    @GetMapping("/{courseId}/enroll")
    public String enrollCourse(@PathVariable Long courseId,
                               @AuthenticationPrincipal CustomUserDetails user) {

        Long userId = user.getId();
        enrollmentService.requestEnrollment(userId, courseId);

        return "redirect:/courses/" + courseId;
    }

    @GetMapping("/{courseId}/cancel")
    public String cancelEnrollment(@PathVariable Long courseId,
                                   @AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getId();
        enrollmentService.cancelEnrollment(userId, courseId);
        return "redirect:/courses/" + courseId;
    }

    @GetMapping("/{courseId}/exams")
    public String courseExams(@PathVariable Long courseId,
                              Authentication auth,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Long userId = user.getId();

        String status = enrollmentService.getEnrollmentStatus(userId, courseId);

        if (!"ENROLLED".equals(status)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "この講義の受講者のみが試験ページにアクセスできます。");
            return "redirect:/courses/" + courseId;
        }

        CourseDetailDto course = courseService.getCourseDetail(courseId, userId);
        List<ExamListDto> exams = examService.getExamsByCourseIdForUser(courseId, userId);

        model.addAttribute("course", course);
        model.addAttribute("exams", exams);
        model.addAttribute("activeTab", "exams");
        return "course-exams";
    }
}
