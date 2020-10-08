package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.impl.UserComponentImpl;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.response.User;
import com.fa20se28.vma.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponentImpl userComponent;

    public UserServiceImpl(UserComponentImpl userComponent) {
        this.userComponent = userComponent;
    }

    @Override
    public DriverPageRes getDrivers(DriverPageReq driverPageReq) {
        DriverPageRes driverPageRes = new DriverPageRes();
        driverPageRes.setDriverList(userComponent
                .findDrivers(driverPageReq.getUserId(),
                        driverPageReq.getName(),
                        driverPageReq.getPhoneNumber(),
                        driverPageReq.getUserStatusId(),
                        driverPageReq.getPage()));
        driverPageRes.setTotalDrivers(userComponent.findTotalUsers(2L));
        return driverPageRes;
    }


    @Override
    public User findDriverById(Long userId) {
        return null;
    }
}
