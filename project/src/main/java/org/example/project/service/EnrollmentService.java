package org.example.project.service;

import lombok.RequiredArgsConstructor;
import org.example.project.mapper.EnrollmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentMapper enrollmentMapper;

    @Transactional
    public void requestEnrollment(Long userId, Long courseId) {
        boolean exists = enrollmentMapper.existsEnrollment(userId, courseId);
        if (exists) return;

        enrollmentMapper.insertEnrollment(userId, courseId);
    }

    @Transactional
    public void cancelEnrollment(Long userId, Long courseId) {
        String status = enrollmentMapper.findStatus(userId, courseId);
        if (!"PENDING".equals(status)) {
            // 수강중/완료 상태는 취소 불가
            return;
        }
        enrollmentMapper.deleteEnrollment(userId, courseId);
    }

    public String getEnrollmentStatus(Long userId, Long courseId) {
        String status = enrollmentMapper.findStatusByUserAndCourse(userId, courseId);
        return status != null ? status : "NOT_ENROLLED";
    }
}