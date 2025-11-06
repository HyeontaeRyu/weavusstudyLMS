package org.example.project.model;


import lombok.Data;

@Data
public class Enrollment {
    private Long id;
    private Long userId;
    private Long courseId;
    private String status;
    private double progressRate;
}