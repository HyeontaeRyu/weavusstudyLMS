package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.project.config.security.CustomUserDetails;
import org.example.project.mapper.CourseMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final CourseMapper courseMapper;

    @GetMapping("/")
    public String index(Authentication auth, Model model) {
        if (auth == null || !auth.isAuthenticated()) {
            log.debug("Not authenticated — redirecting to login");
            return "redirect:/auth/login";
        }

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Long userId = user.getId();

        int enrolledCount = courseMapper.countByStatus(userId, "ENROLLED");
        int completedCount = courseMapper.countByStatus(userId, "COMPLETED");

        model.addAttribute("enrolledCount", enrolledCount);
        model.addAttribute("completedCount", completedCount);
        return "index";
    }
}