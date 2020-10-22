package com.fa20se28.vma.service;

import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserReq;

public interface FirebaseService {
    void createUserRecord(UserReq userReq);

    void deleteUserRecord(String userId);

    void updateUserRecord(DriverReq driverReq);
}
