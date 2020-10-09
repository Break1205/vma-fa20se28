package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.impl.UserComponentImpl;
import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.response.User;
import com.fa20se28.vma.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponentImpl userComponent;

    public UserServiceImpl(UserComponentImpl userComponent) {
        this.userComponent = userComponent;
    }

    @Override
    public List<Driver> getDrivers(DriverPageReq driverPageReq) {
        return userComponent
                .findDrivers(
                        driverPageReq.getUserId(),
                        driverPageReq.getName(),
                        driverPageReq.getPhoneNumber(),
                        driverPageReq.getUserStatusId(),
                        driverPageReq.getPage());
    }

    @Override
    public int getTotalDrivers() {
        return userComponent.findTotalDrivers();
    }

    @Override
    public List<Contributor> getContributors(ContributorPageReq contributorPageReq) {
        return userComponent
                .findContributors(
                        contributorPageReq.getUserId(),
                        contributorPageReq.getName(),
                        contributorPageReq.getPhoneNumber(),
                        contributorPageReq.getTotalVehicles(),
                        contributorPageReq.getPage());
    }

    @Override
    public int getTotalContributor() {
        return userComponent.findTotalContributors();
    }


    @Override
    public User findDriverById(Long userId) {
        return null;
    }
}
