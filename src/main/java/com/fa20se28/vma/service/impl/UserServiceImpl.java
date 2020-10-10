package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.component.impl.UserComponentImpl;
import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;

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
    public int getTotalDriversOrTotalFilteredDriver(DriverPageReq driverPageReq) {
        if (driverPageReq.getUserId() != null
                || driverPageReq.getName() != null
                || driverPageReq.getPhoneNumber() != null
                || driverPageReq.getUserStatusId() != null) {
            return getTotalDriversWhenFiltering(driverPageReq);
        }
        return getTotalDrivers();
    }

    private int getTotalDrivers() {
        return userComponent.findTotalDrivers();
    }

    private int getTotalDriversWhenFiltering(DriverPageReq driverPageReq) {
        return userComponent
                .findTotalDriversWhenFilter(
                        driverPageReq.getUserId(),
                        driverPageReq.getName(),
                        driverPageReq.getPhoneNumber(),
                        driverPageReq.getUserStatusId());
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
    public int getTotalContributorsOrTotalFilteredContributors(ContributorPageReq contributorPageReq) {
        if (contributorPageReq.getUserId() != null
                || contributorPageReq.getName() != null
                || contributorPageReq.getPhoneNumber() != null
                || contributorPageReq.getTotalVehicles() != null) {
            return getTotalContributorsWhenFiltering(contributorPageReq);
        }
        return getTotalContributors();
    }


    private int getTotalContributors() {
        return userComponent.findTotalContributors();
    }

    private int getTotalContributorsWhenFiltering(ContributorPageReq contributorPageReq) {
        return userComponent
                .findTotalContributorsWhenFilter(
                        contributorPageReq.getUserId(),
                        contributorPageReq.getName(),
                        contributorPageReq.getPhoneNumber(),
                        contributorPageReq.getTotalVehicles());
    }
}
