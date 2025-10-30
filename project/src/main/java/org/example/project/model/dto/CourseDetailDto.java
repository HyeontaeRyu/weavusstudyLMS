package org.example.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetailDto {
    private Long id;
    private String title;
    private String teacherName;
    private String descriptionSimple;
    private String description;
    private String enrollmentStatus;
    private Double progressRate;
    private Date deadline;
    private String overallGrade;

    private List<LectureSectionDto> sections;
}