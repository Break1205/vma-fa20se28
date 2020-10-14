package com.fa20se28.vma.service;

import com.fa20se28.vma.request.DriverReq;
import com.google.firebase.auth.FirebaseAuthException;

public interface FirebaseService {
    void createUserRecord(DriverReq driverReq) throws FirebaseAuthException;

    void deleteUserRecord(String userId) throws FirebaseAuthException;
}
