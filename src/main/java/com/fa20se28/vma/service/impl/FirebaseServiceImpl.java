package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.service.FirebaseService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.messaging.FirebaseMessaging;
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
    public void createUserRecord(DriverReq driverReq) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setPhoneNumber("+84" + driverReq.getPhoneNumber())
                .setDisplayName(driverReq.getFullName())
                .setPhotoUrl(driverReq.getImageLink())
                .setUid(driverReq.getUserId())
                .setDisabled(false);

        FirebaseAuth.getInstance().createUser(request);
    }
}
