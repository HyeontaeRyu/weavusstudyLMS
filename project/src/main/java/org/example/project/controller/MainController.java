package org.example.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    /* TODO:
     * - ENROLLMENT 테이블 이거 프로젝트에 맞게 수정
     * - 대쉬보드 수강중코스 완료코스 평균평가 등 맞게 뜨게 수정
     * - ENROLLMENT의 경우,
     * */

    @GetMapping
    public String index(Authentication auth, Model model) {
        if (auth == null || !auth.isAuthenticated()) {
            log.debug("Not authenticated — redirecting to login");
            return "redirect:/auth/login";
        }
        
        return "index";
    }
}
