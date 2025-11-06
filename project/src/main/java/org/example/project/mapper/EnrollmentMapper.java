package org.example.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.project.model.Enrollment;
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


    @Select("""
                SELECT user_id, course_id, progress_rate
                FROM ENROLLMENTS
                WHERE status = 'ENROLLED'
                  AND progress_rate < 100
            """)
    List<Enrollment> findIncompleteEnrollments();
}