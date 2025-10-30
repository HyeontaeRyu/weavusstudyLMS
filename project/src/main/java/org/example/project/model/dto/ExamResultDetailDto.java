package org.example.project.model.dto;

import lombok.Data;

@Data
public class ExamResultDetailDto {
    private Long examId;
    private String examTitle;
    private Integer totalScore;
    private Double userScore;
    private String resultStatus;

    private Long questionId;
    private String questionText;
    private String questionType;
    private String correctAnswer;
    private String userAnswer;
    private Boolean isCorrect;
}