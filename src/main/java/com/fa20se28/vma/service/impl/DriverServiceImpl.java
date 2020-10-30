package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.DriverComponent;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserReq;
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
        if (success == 1) {
            UserReq userReq = new UserReq(
                    driverReq.getUserId(),
                    driverReq.getFullName(),
                    driverReq.getPhoneNumber(),
                    driverReq.getImageLink());
            firebaseService.createUserRecord(userReq);
            return 1;
        }
        return 0;
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
        driverPageRes.setDriverRes(driverComponent.findDrivers(driverPageReq));
        return driverPageRes;
    }

    @Override
    public int getTotalDriversOrTotalFilteredDriver(DriverPageReq driverPageReq) {
        if (driverPageReq.getUserId() != null
                || driverPageReq.getFullName() != null
                || driverPageReq.getPhoneNumber() != null
                || driverPageReq.getUserStatus() != null) {
            return driverComponent
                    .findTotalDriversWhenFiltering(driverPageReq);
        }
        return driverComponent.findTotalDrivers();
    }

    @Transactional
    @Override
    public void deleteUserByUserId(String userId) {
        if (driverComponent.deleteDriverById(userId) == 1) {
            firebaseService.deleteUserRecord(userId);
        }

    }

    @Override
    public void updateDriver(DriverReq driverReq) {
        if (driverComponent.updateDriverByUserId(driverReq) == 1) {
            firebaseService.updateUserRecord(driverReq);
        }
    }
}
