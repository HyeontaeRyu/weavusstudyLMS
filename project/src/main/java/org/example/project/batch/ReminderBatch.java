package org.example.project.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.project.mapper.CourseMapper;
import org.example.project.mapper.EnrollmentMapper;
import org.example.project.model.Enrollment;
import org.example.project.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReminderBatch {

    private final EnrollmentMapper enrollmentMapper;
    private final CourseMapper courseMapper;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 3 * * MON")
    public void sendUnstartedCourseReminders() {
        log.info("=== 未受講者リマインダーバッチ開始 ===");

        List<Enrollment> incompleteCourses = enrollmentMapper.findIncompleteEnrollments();

        for (Enrollment e : incompleteCourses) {
            String courseTitle = courseMapper.findTitleById(e.getCourseId());
            double rate = e.getProgressRate();

            String message = "講義『" + courseTitle + "』の学習がまだ完了していません。"
                    + "現在の進捗率：" + rate + "%";

            notificationService.sendCustomNotification(
                    e.getUserId(),
                    e.getCourseId(),
                    message
            );
        }
        log.info("=== リマインダー送信完了: {}件 ===", incompleteCourses.size());
    }
}