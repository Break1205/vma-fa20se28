package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.configuration.exception.InvalidFirebaseMessagingException;
import com.fa20se28.vma.configuration.exception.InvalidFirebaseTokenException;
import com.fa20se28.vma.enums.NotificationType;
import com.fa20se28.vma.model.ClientRegistrationToken;
import com.fa20se28.vma.model.NotificationData;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.request.UserTokenReq;
import com.fa20se28.vma.service.FirebaseService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    private static final String CONFIG_FILE = "googleConfig.json";

    @Bean
    @Primary
    public void firebaseInitialization() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.
                            fromStream(new ClassPathResource(CONFIG_FILE).getInputStream()))
                    .setDatabaseUrl("https://vma-fa20se28.firebaseio.com").build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUserRecord(UserReq userReq) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest();
        request
                .setPhoneNumber("+84" + userReq.getPhoneNumber())
                .setDisplayName(userReq.getFullName())
                .setPhotoUrl(userReq.getImageLink())
                .setUid(userReq.getUserId())
                .setDisabled(false);
        try {
            FirebaseAuth.getInstance().createUser(request);
        } catch (FirebaseAuthException e) {
            throw new InvalidFirebaseTokenException("Invalid Firebase Token", e, e.getAuthErrorCode());
        }
    }

    @Override
    public void deleteUserRecord(String userId) {
        try {
            FirebaseAuth.getInstance().deleteUser(userId);
        } catch (FirebaseAuthException e) {
            throw new InvalidFirebaseTokenException("Invalid Firebase Token", e, e.getAuthErrorCode());
        }
    }

    @Override
    public void updateUserRecord(UserReq userReq) {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(userReq.getUserId())
                .setPhoneNumber("+84" + userReq.getPhoneNumber())
                .setDisplayName(userReq.getFullName())
                .setPhotoUrl(userReq.getImageLink())
                .setDisabled(false);

        try {
            FirebaseAuth.getInstance().updateUser(request);
        } catch (FirebaseAuthException e) {
            throw new InvalidFirebaseTokenException("Invalid Firebase Token", e, e.getAuthErrorCode());
        }
    }

    @Override
    public String decodeToken(UserTokenReq token) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(token.getIdToken()).getUid();
        } catch (FirebaseAuthException e) {
            throw new InvalidFirebaseTokenException("Invalid Firebase Token", e, e.getAuthErrorCode());
        }
    }

    @Override
    public void notifyUserByFCMToken(ClientRegistrationToken fcmToken, NotificationData notificationData) {
        String title = "";
        if (notificationData.getNotificationType().equals(NotificationType.REQUEST_ACCEPTED)) {
            title = "Hurray! Your request is accepted";
        } else if (notificationData.getNotificationType().equals(NotificationType.REQUEST_DENIED)) {
            title = "Sorry! Your request is denied";
        }
        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(notificationData.getBody())
                                .build())
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setTtl(3600 * 1000)
                                .setNotification(AndroidNotification.builder()
                                        .setImage("https://epic7x.com/wp-content/uploads/2020/11/Sword-of-Summer-Twilight.png")
                                        .setColor("#87CEEB")
                                        .build())
                                .build())
                .setApnsConfig(
                        ApnsConfig.builder()
                                .setAps(
                                        Aps.builder()
                                                .setBadge(69)
                                                .build())
                                .build())
                .setToken(fcmToken.getToken())
                .build();
        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new InvalidFirebaseMessagingException("Firebase Messaging Exception: ", e, e.getMessagingErrorCode());
        }
    }
}
