package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.impl.UserComponentImpl;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.response.ContributorPageRes;
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
        if (driverPageReq.getName() == null
                && driverPageReq.getPhoneNumber() == null
                && driverPageReq.getUserId() == null
                && driverPageReq.getUserStatusId() == null) {
            driverPageRes.setDriverList(
                    userComponent
                            .findDrivers(driverPageReq.getPage()));
        } else {
            driverPageRes.setDriverList(
                    userComponent
                            .searchDrivers(
                                    driverPageReq.getUserId(),
                                    driverPageReq.getName(),
                                    driverPageReq.getPhoneNumber(),
                                    driverPageReq.getUserStatusId(),
                                    driverPageReq.getPage()));
        }
        driverPageRes.setTotalDrivers(userComponent.findTotalUsers(3L));
        return driverPageRes;
    }

    @Override
    public ContributorPageRes getContributors(ContributorPageReq contributorPageReq) {
        ContributorPageRes contributorPageRes = new ContributorPageRes();
        if (contributorPageReq.getName() == null
                && contributorPageReq.getPhoneNumber() == null
                && contributorPageReq.getTotalVehicles().equals(0L)){
            contributorPageRes.setContributorList(
                    userComponent
                            .findContributors(contributorPageReq.getPage()));
        } else {
            contributorPageRes.setContributorList(
                    userComponent
                            .searchContributors(
                                    contributorPageReq.getUserId(),
                                    contributorPageReq.getName(),
                                    contributorPageReq.getPhoneNumber(),
                                    contributorPageReq.getTotalVehicles(),
                                    contributorPageReq.getPage()));
        }
        contributorPageRes.setTotalContributor(userComponent.findTotalUsers(2L));
        return contributorPageRes;
    }


    @Override
    public User findDriverById(Long userId) {
        return null;
    }
}
