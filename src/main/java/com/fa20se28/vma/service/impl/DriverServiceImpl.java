package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.DriverComponent;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.response.DriverDetailRes;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.service.DriverService;
import com.fa20se28.vma.service.FirebaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DriverServiceImpl implements DriverService {
    private final DriverComponent driverComponent;
    private final FirebaseService firebaseService;

    public DriverServiceImpl(DriverComponent driverComponent, FirebaseService firebaseService) {
        this.driverComponent = driverComponent;
        this.firebaseService = firebaseService;
    }

    @Override
    @Transactional
    public int createDriver(DriverReq driverReq) {
        int success = driverComponent.createDriver(driverReq);
        if (success == 1 && driverReq.getUserStatusId() == 2) {
            firebaseService.createUserRecord(driverReq, "DRIVER");
        }
        return success;
    }

    @Override
    public DriverDetailRes getDriverById(String userId) {
        DriverDetail driverDetail = driverComponent.findDriverById(userId);
        DriverDetailRes driverDetailRes = new DriverDetailRes();
        driverDetailRes.setDriverDetail(driverDetail);
        return driverDetailRes;
    }

    @Override
    public DriverPageRes getDrivers(DriverPageReq driverPageReq) {
        DriverPageRes driverPageRes = new DriverPageRes();
        driverPageRes.setDriverList(driverComponent
                .findDrivers(
                        driverPageReq.getUserId(),
                        driverPageReq.getName(),
                        driverPageReq.getPhoneNumber(),
                        driverPageReq.getUserStatusId(),
                        driverPageReq.getPage()));
        return driverPageRes;
    }

    @Override
    public int getTotalDriversOrTotalFilteredDriver(DriverPageReq driverPageReq) {
        if (driverPageReq.getUserId() != null
                || driverPageReq.getName() != null
                || driverPageReq.getPhoneNumber() != null
                || driverPageReq.getUserStatusId() != null) {
            return getTotalDriversWhenFiltering(driverPageReq);
        }
        return getTotalDrivers();
    }

    @Transactional
    @Override
    public void deleteUserByUserId(String userId) {
        firebaseService.deleteUserRecord(userId);
        driverComponent.deleteDriverById(userId);
    }

    @Override
    public void updateDriver(DriverReq driverReq) {
        firebaseService.updateUserRecord(driverReq);
        driverComponent.updateDriverByUserId(driverReq);
    }

    private int getTotalDrivers() {
        return driverComponent.findTotalDrivers();
    }

    private int getTotalDriversWhenFiltering(DriverPageReq driverPageReq) {
        return driverComponent
                .findTotalDriversWhenFilter(
                        driverPageReq.getUserId(),
                        driverPageReq.getName(),
                        driverPageReq.getPhoneNumber(),
                        driverPageReq.getUserStatusId());
    }
}
