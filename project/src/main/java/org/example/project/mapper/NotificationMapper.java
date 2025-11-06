package org.example.project.mapper;

import org.apache.ibatis.annotations.*;
import org.example.project.model.Notification;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Select("""
                SELECT *
                FROM NOTIFICATIONS
                WHERE user_id = #{userId}
                ORDER BY sent_at DESC
            """)
    List<Notification> selectByUserId(Long userId);

    @Update("""
                UPDATE NOTIFICATIONS
                SET is_read = TRUE
                WHERE id = #{id}
            """)
    void markAsRead(Long id);

    @Delete("DELETE FROM NOTIFICATIONS WHERE id = #{id}")
    void deleteNotification(Long id);

    @Insert("""
                INSERT INTO NOTIFICATIONS (user_id, course_id, message, status, sent_at, is_read)
                VALUES (#{userId}, #{courseId}, #{message}, #{status}, #{sentAt}, #{isRead})
            """)
    void insertNotification(Notification n);

    @Select("""
                SELECT *
                FROM NOTIFICATIONS
                WHERE status = 'PENDING'
                ORDER BY sent_at ASC
            """)
    List<Notification> selectPendingNotifications();

    @Update("""
                UPDATE NOTIFICATIONS
                SET status = 'SENT'
                WHERE id = #{id}
            """)
    void markAsSent(Long id);
}
