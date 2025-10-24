package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.project.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping
    public String index(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            log.debug("Authentication object is null or not authenticated");
            return "index";
        }
        log.debug("Authentication object is authenticated");

        var authorities = authentication.getAuthorities();
        boolean isStudent = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ROLE_STUDENT.name()));

        if (isStudent) {
            return "student";
        }

        return "student";
    }
}
