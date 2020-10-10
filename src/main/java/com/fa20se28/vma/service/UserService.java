package com.fa20se28.vma.service;

import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.DriverPageReq;

import java.util.List;

public interface UserService {
    List<Driver> getDrivers(DriverPageReq driverPageReq);

    int getTotalDriversOrTotalFilteredDriver(DriverPageReq driverPageReq);

    List<Contributor> getContributors(ContributorPageReq contributorPageReq);

    int getTotalContributorsOrTotalFilteredContributors(ContributorPageReq contributorPageReq);

}
