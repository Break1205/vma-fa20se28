package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.DriverComponent;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.IssuedDriversPageReq;
import com.fa20se28.vma.response.DriverDetailRes;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.response.IssuedDriversPageRes;
import com.fa20se28.vma.service.DriverService;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {
    private final DriverComponent driverComponent;

    public DriverServiceImpl(DriverComponent driverComponent) {
        this.driverComponent = driverComponent;
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

    @Override
    public IssuedDriversPageRes getIssuedDrivers(String contributorId, IssuedDriversPageReq issuedDriversPageReq) {
        IssuedDriversPageRes issuedDriversPageRes = new IssuedDriversPageRes();
        issuedDriversPageRes.setDrivers(
                driverComponent
                        .findIssuedDrivers(contributorId, issuedDriversPageReq));
        return issuedDriversPageRes;
    }

    @Override
    public int getTotalIssuedDrivers(String contributorId, IssuedDriversPageReq issuedDriversPageReq) {
        return driverComponent
                .findTotalIssuedDrivers(contributorId, issuedDriversPageReq);
    }

}
