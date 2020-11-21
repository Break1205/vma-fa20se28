package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.NotificationType;

public class NotificationData {
    private NotificationType notificationType;
    private String body;
    // id of request/contract/contractVehicle not userId,
    // because we user fcm token to send to the specific client mobile already
    // this is for mobile/web to handle their flow
    private String id;

    public NotificationData(NotificationType notificationType, String body, String id) {
        this.notificationType = notificationType;
        this.body = body;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
