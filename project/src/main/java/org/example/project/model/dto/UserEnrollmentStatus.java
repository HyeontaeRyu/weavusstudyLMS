package org.example.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEnrollmentStatus {
    private String id;
    private String title;
    private String descriptionSimple;
    private String description;
    private String enrollmentStatus; // "NOT_ENROLLED", "PENDING", "ENROLLED", "COMPLETED"
    private double progressRate;
    private Date deadline;
    private String teacherName;

}