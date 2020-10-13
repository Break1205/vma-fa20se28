package com.fa20se28.vma.service;

import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.response.DriverDetailRes;
import com.fa20se28.vma.response.DriverPageRes;

public interface DriverService {
    DriverDetailRes getDriverById(String userId);

    DriverPageRes getDrivers(DriverPageReq driverPageReq);

    int getTotalDriversOrTotalFilteredDriver(DriverPageReq driverPageReq);
}
