package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.NotificationType;

import java.time.LocalDateTime;

public class NotificationData {
    private NotificationType notificationType;
    private LocalDateTime notificationTime;
    private String body;

    public NotificationData(NotificationType notificationType, String body, LocalDateTime notificationTime) {
        this.notificationType = notificationType;
        this.body = body;
        this.notificationTime = notificationTime;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }
}
