package com.fa20se28.vma.service;

import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.response.DriverDetailRes;
import com.fa20se28.vma.response.DriverPageRes;
import com.google.firebase.auth.FirebaseAuthException;

public interface DriverService {
    int createDriver(DriverReq driverReq) throws FirebaseAuthException;

    DriverDetailRes getDriverById(String userId);

    DriverPageRes getDrivers(DriverPageReq driverPageReq);

    int getTotalDriversOrTotalFilteredDriver(DriverPageReq driverPageReq);

    void deleteUserByUserId(String userId) throws FirebaseAuthException;

    void updateDriver(DriverReq driverReq) throws FirebaseAuthException;
}
