package org.example.project.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Exam {
    private Long id;
    private Long courseId;
    private String title;
    private String description;
    private Integer totalScore;
    private Integer durationMinutes;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}