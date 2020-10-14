package com.fa20se28.vma.service;

import com.fa20se28.vma.request.DriverReq;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    int createDriver(DriverReq driverReq) throws FirebaseAuthException;

    void deleteUserByUserId(String userId) throws FirebaseAuthException;
}
