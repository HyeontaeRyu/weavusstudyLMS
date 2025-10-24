package org.example.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.project.config.security.JwtProvider;
import org.example.project.model.Role;
import org.example.project.model.User;
import org.example.project.model.dto.SignupRequest;
import org.example.project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        if (!model.containsAttribute("signupRequest")) {
            model.addAttribute("signupRequest", new SignupRequest());
        }

        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(
            @Valid @ModelAttribute("signupRequest") SignupRequest req,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.signupRequest", bindingResult
            );
            redirectAttributes.addFlashAttribute("signupRequest", req);
            return "redirect:/auth/signup";
        }
        try {
            User user = userService.register(req.getName(), req.getEmail(), req.getPassword());

        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "duplicate",
                    "メールアドレスは既に使用されています。");
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.signupRequest", bindingResult
            );
            redirectAttributes.addFlashAttribute("signupRequest", req);
            return "redirect:/auth/signup";
        }

        redirectAttributes.addFlashAttribute("signupSuccess", "会員登録が成功しました。");
        return "redirect:/auth/login";
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Refresh token is missing"));
        }

        if (!jwtProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid or expired refresh token"));
        }

        String email = jwtProvider.getEmail(refreshToken);
        Role role = jwtProvider.getRole(refreshToken);
        String newAccessToken = jwtProvider.createAccessToken(email, role);

        log.info("Refresh token used by {} → new access token issued", email);

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "message", "Access token refreshed successfully"
        ));
    }
}
