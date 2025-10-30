package org.example.project.service;

import lombok.RequiredArgsConstructor;
import org.example.project.mapper.CourseMapper;
import org.example.project.model.User;
import org.example.project.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseMapper courseMapper;

    public int countUserCourses(User user) {
        return courseMapper.countUserCourse(user.getId());
    }

    public List<UserEnrollmentStatus> getCourseEnrollmentStatus(long userId) {
        return courseMapper.findAllCoursesWithStatus(userId);
    }

    public Page<UserEnrollmentStatus> getPagedCourses(Long userId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);

        int totalCount = courseMapper.countAllCourses();
        List<UserEnrollmentStatus> courses =
                courseMapper.findAllCoursesWithStatusPaged(userId, pageRequest);

        return new Page<>(courses, page, size, totalCount);
    }

    public CourseDetailDto getCourseDetail(Long courseId, Long userId) {
        CourseDetailDto course = courseMapper.findCourseDetail(courseId, userId);
        List<LectureSectionDto> sections = courseMapper.findSectionsWithLectures(courseId);
        course.setSections(sections);
        return course;
    }


}
