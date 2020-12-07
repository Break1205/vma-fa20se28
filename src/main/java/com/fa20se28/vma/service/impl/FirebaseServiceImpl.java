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
import com.google.firebase.messaging.ApsAlert;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
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
            title = "Hurray! Your request has been accepted";
        } else if (notificationData.getNotificationType().equals(NotificationType.REQUEST_DENIED)) {
            title = "Sorry! Your request has been denied";
        } else if (notificationData.getNotificationType().equals(NotificationType.CONTRACT_ASSIGNED)) {
            title = "Contract Assigned";
        } else if (notificationData.getNotificationType().equals(NotificationType.LICENSE_EXPIRED)) {
            title = "Your license is almost expired";
        } else if (notificationData.getNotificationType().equals(NotificationType.START_TRIP)) {
            title = "Contract Vehicle Status";
        } else if (notificationData.getNotificationType().equals(NotificationType.END_TRIP)) {
            title = "Contract Vehicle Status";
        } else if (notificationData.getNotificationType().equals(NotificationType.CONTRACT_STARTED)) {
            title = "Contract Status";
        }else if (notificationData.getNotificationType().equals(NotificationType.CONTRACT_COMPLETED)) {
            title = "Contract Status";
        } else if (notificationData.getNotificationType().equals(NotificationType.VEHICLE_CHANGED)) {
            title = "Vehicle Reassigned";
        }
        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(notificationData.getBody())
                                .build())
                .setAndroidConfig( // android
                        AndroidConfig.builder()
                                .setTtl(3600 * 1000)
                                .setNotification(
                                        AndroidNotification.builder()
                                                .setColor("#87CEEB")
                                                .setTitle(title)
                                                .setBody(notificationData.getBody())
                                                .setDefaultSound(true)
                                                .setDefaultVibrateTimings(true)
                                                .build())
                                .putData("id", notificationData.getId())
                                .putData("notificationType", notificationData.getNotificationType().toString())
                                .build())
                .setWebpushConfig( // web
                        WebpushConfig.builder()
                                .setNotification(
                                        WebpushNotification.builder()
                                                .setTitle(title)
                                                .setBody(notificationData.getBody())
                                                .setSilent(false)
                                                .setRenotify(true)
                                                .setRequireInteraction(true)
                                                .build())
                                .putData("id", notificationData.getId())
                                .putData("notificationType", notificationData.getNotificationType().toString())
                                .build()
                )
                .setApnsConfig( // ios
                        ApnsConfig.builder()
                                .setAps(
                                        Aps.builder()
                                                .setAlert(
                                                        ApsAlert.builder()
                                                                .setTitle(title)
                                                                .setBody(notificationData.getBody())
                                                                .build())
                                                .putCustomData("id", notificationData.getId())
                                                .putCustomData("notificationType", notificationData.getNotificationType().toString())
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
