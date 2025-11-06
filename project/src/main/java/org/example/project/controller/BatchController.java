package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.project.batch.ReminderBatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BatchController {

    private final ReminderBatch reminderBatch;

    @GetMapping("/batch/run-reminder")
    public String runReminderBatch() {
        log.info("手動で未受講リマインダーバッチを実行します...");
        reminderBatch.sendUnstartedCourseReminders();
        return "リマインダーバッチを手動で実行しました。ログを確認してください。";
    }
}
