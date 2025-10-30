package org.example.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamListDto {
    private Long examId;
    private Long courseId;
    private String courseTitle;
    private String examTitle;
    private boolean isTaken;
    private LocalDateTime deadline;
    private Integer durationMinutes;
    private Integer totalScore;
    private String description;
    private String resultStatus;
}