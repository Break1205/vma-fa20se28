package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.DriverReq;

import java.util.List;

public interface DriverComponent {
    DriverDetail findDriverById(String userId);

    List<Driver> findDrivers(DriverPageReq driverPageReq);

    int findTotalDrivers();

    int findTotalDriversWhenFiltering(DriverPageReq driverPageReq);

    int createDriver(DriverReq driverReq);

    void deleteDriverById(String userId);

    void updateDriverByUserId(DriverReq driverReq);


}
