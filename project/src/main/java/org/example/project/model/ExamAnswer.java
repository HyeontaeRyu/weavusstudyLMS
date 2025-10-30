package org.example.project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamAnswer {
    private Long id;
    private Long examResultId;
    private Long questionId;
    private String userAnswer;
    private Boolean isCorrect;
    private LocalDateTime answeredAt;
}