package com.fa20se28.vma.service;

import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.IssuedDriversPageReq;
import com.fa20se28.vma.response.DriverDetailRes;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.response.IssuedDriversPageRes;

public interface DriverService {

    DriverDetailRes getDriverById(String userId);

    DriverPageRes getDrivers(DriverPageReq driverPageReq);

    int getTotalDriversOrTotalFilteredDriver(DriverPageReq driverPageReq);

    IssuedDriversPageRes getIssuedDrivers(String contributorId, IssuedDriversPageReq issuedDriversPageReq);

    int getTotalIssuedDrivers(String contributorId, IssuedDriversPageReq issuedDriversPageReq);
}
