package org.example.project.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamQuestion {
    private Long id;
    private Long examId;
    private String questionText;
    private String questionType;
    private String options;
    private String correctAnswer;
    private Integer score;
    private Integer orderIndex;
    private LocalDateTime createdAt;

    private List<String> parsedOptions;
}