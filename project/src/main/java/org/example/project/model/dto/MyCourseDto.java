package org.example.project.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyCourseDto {
    private Long id;
    private String title;
    private BigDecimal progressRate;
    private String status; // ENROLLED or COMPLETED
    private String finalGrade; // e.g. "A", "B", "C"
}
