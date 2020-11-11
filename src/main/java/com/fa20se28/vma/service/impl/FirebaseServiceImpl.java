package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.configuration.exception.InvalidFirebaseTokenException;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.request.UserTokenReq;
import com.fa20se28.vma.service.FirebaseService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    @Value("${firebase.credential.resource-path}")
    private String keyPath;

    @Bean
    @Primary
    public void firebaseInitialization() throws IOException {
        Resource resource = new ClassPathResource(keyPath);
        FileInputStream serviceAccount = new FileInputStream(resource.getFile());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://vma-fa20se28.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
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


}
