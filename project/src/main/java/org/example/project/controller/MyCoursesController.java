package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.config.security.CustomUserDetails;
import org.example.project.mapper.EnrollmentMapper;
import org.example.project.model.dto.MyCourseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mycourses")
public class MyCoursesController {

    private final EnrollmentMapper enrollmentMapper;

    @GetMapping
    public String myCourses(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        Long userId = user.getId();
        List<MyCourseDto> myCourses = enrollmentMapper.findMyCourses(userId);
        model.addAttribute("myCourses", myCourses);
        return "mycourses";
    }
}
