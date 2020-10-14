package com.fa20se28.vma.service;

import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserReq;
import com.google.firebase.auth.FirebaseAuthException;

public interface FirebaseService {
    void createUserRecord(UserReq userReq,String role) throws FirebaseAuthException;

    void deleteUserRecord(String userId) throws FirebaseAuthException;

    void updateUserRecord(DriverReq driverReq) throws FirebaseAuthException;
}
