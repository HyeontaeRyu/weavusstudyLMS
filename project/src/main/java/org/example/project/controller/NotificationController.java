package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.config.security.CustomUserDetails;
import org.example.project.model.Notification;
import org.example.project.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public String list(Authentication auth, Model model) {
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Long userId = user.getId();

        List<Notification> notifications = notificationService.getUserNotifications(userId);
        model.addAttribute("notifications", notifications);

        return "notification-list";
    }

    @PostMapping("/{id}/read")
    @ResponseBody
    public String markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return "OK";
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "OK";
    }
}
