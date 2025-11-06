package org.example.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.project.model.dto.CourseDetailDto;
import org.example.project.model.dto.LectureSectionDto;
import org.example.project.model.dto.PageRequest;
import org.example.project.model.dto.UserEnrollmentStatus;

import java.util.List;

@Mapper
public interface CourseMapper {

    int countUserCourse(@Param("userId") Long userId);

    int countAllCourses();

    List<UserEnrollmentStatus> findAllCoursesWithStatus(@Param("userId") Long userId);

    List<UserEnrollmentStatus> findAllCoursesWithStatusPaged(
            @Param("userId") Long userId,
            @Param("page") PageRequest page
    );

    CourseDetailDto findCourseDetail(
            @Param("courseId") Long courseId,
            @Param("userId") Long userId
    );

    List<LectureSectionDto> findSectionsWithLectures(Long courseId);

    int countByStatus(@Param("userId") Long userId, @Param("status") String status);
    
    @Select("SELECT title FROM COURSES WHERE id = #{courseId}")
    String findTitleById(Long courseId);
}
