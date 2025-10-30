package org.example.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.project.model.dto.MyCourseDto;

import java.util.List;

@Mapper
public interface EnrollmentMapper {

    boolean existsEnrollment(@Param("userId") Long userId,
                             @Param("courseId") Long courseId);

    void insertEnrollment(@Param("userId") Long userId,
                          @Param("courseId") Long courseId);

    String findStatus(@Param("userId") Long userId,
                      @Param("courseId") Long courseId);

    void deleteEnrollment(@Param("userId") Long userId,
                          @Param("courseId") Long courseId);

    List<MyCourseDto> findMyCourses(Long userId);

    String findStatusByUserAndCourse(Long userId, Long courseId);
}