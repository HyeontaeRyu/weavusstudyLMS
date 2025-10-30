package org.example.project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamResult {
    private Long id;
    private Long userId;
    private Long examId;
    private Double score;
    private LocalDateTime submittedAt;
}