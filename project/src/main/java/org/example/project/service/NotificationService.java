package org.example.project.service;

import lombok.RequiredArgsConstructor;
import org.example.project.mapper.NotificationMapper;
import org.example.project.model.Notification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;

    public void sendExamResultNotification(Long userId, Long examId, double score) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setCourseId(examId);
        n.setMessage("試験の結果が公開されました: " + score + "点");
        n.setSentAt(LocalDateTime.now());
        n.setStatus("SENT");
        n.setRead(false);
        notificationMapper.insertNotification(n);
    }

    public void createReminderNotification(Long userId, Long courseId) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setCourseId(courseId);
        n.setMessage("講義がまだ未受講です。進捗を確認しましょう。");
        n.setStatus("PENDING");
        n.setSentAt(LocalDateTime.now());
        n.setRead(false);
        notificationMapper.insertNotification(n);
    }

    public void sendReminderNotification(Long userId, Long courseId, String courseTitle) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setCourseId(courseId);
        n.setMessage("講義『" + courseTitle + "』がまだ未完了です。進捗を確認しましょう。");
        n.setStatus("SENT");
        n.setSentAt(LocalDateTime.now());
        n.setRead(false);
        notificationMapper.insertNotification(n);
    }

    public void sendCustomNotification(Long userId, Long courseId, String message) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setCourseId(courseId);
        n.setMessage(message);
        n.setStatus("SENT");
        n.setSentAt(LocalDateTime.now());
        n.setRead(false);
        notificationMapper.insertNotification(n);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationMapper.selectByUserId(userId);
    }

    public List<Notification> getPendingNotifications() {
        return notificationMapper.selectPendingNotifications();
    }

    public void markAsSent(Long id) {
        notificationMapper.markAsSent(id);
    }

    public void markAsRead(Long id) {
        notificationMapper.markAsRead(id);
    }

    public void deleteNotification(Long id) {
        notificationMapper.deleteNotification(id);
    }
}
