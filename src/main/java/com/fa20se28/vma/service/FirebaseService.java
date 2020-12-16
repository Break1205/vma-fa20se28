package com.fa20se28.vma.service;

import com.fa20se28.vma.model.ClientRegistrationToken;
import com.fa20se28.vma.model.NotificationData;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.request.UserTokenReq;

public interface FirebaseService {
    void createUserRecord(UserReq userReq);

    void deleteUserRecord(String userId);

    void updateUserRecord(UserReq userReq);

    String decodeToken(UserTokenReq token);

    void notifyUserByFCMToken(ClientRegistrationToken fcmToken, NotificationData notificationData);

    void notifySubscribersByTopic(String topic, NotificationData notificationData);

    void subscribeUserToTopic(ClientRegistrationToken clientRegistrationToken, String admin);
}
