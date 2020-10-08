package com.fa20se28.vma.service;

import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.response.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    DriverPageRes getDrivers(DriverPageReq driverPageReq);

    User findDriverById(Long userId);
}
