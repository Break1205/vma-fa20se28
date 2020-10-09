package com.fa20se28.vma.service;

import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.response.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<Driver> getDrivers(DriverPageReq driverPageReq);

    int getTotalDrivers();

    List<Contributor> getContributors(ContributorPageReq contributorPageReq);

    int getTotalContributor();

    User findDriverById(Long userId);
}
