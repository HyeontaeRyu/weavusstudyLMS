package org.example.project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notification {
    private Long id;
    private Long userId;
    private Long courseId;
    private String message;
    private LocalDateTime sentAt;
    private String status;
    private boolean isRead;
}
