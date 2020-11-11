package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ContributorComponent;
import com.fa20se28.vma.model.ContributorDetail;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.response.ContributorDetailRes;
import com.fa20se28.vma.response.ContributorPageRes;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.response.UserPageRes;
import com.fa20se28.vma.service.ContributorService;
import org.springframework.stereotype.Service;

@Service
public class ContributorServiceImpl implements ContributorService {
    private final ContributorComponent contributorComponent;

    public ContributorServiceImpl(ContributorComponent contributorComponent) {
        this.contributorComponent = contributorComponent;
    }

    @Override
    public ContributorDetailRes getContributorById(String userId) {
        ContributorDetail contributorDetail = contributorComponent.findContributorById(userId);
        ContributorDetailRes contributorDetailRes = new ContributorDetailRes();
        contributorDetailRes.setContributorDetail(contributorDetail);
        return contributorDetailRes;
    }

    @Override
    public ContributorPageRes getContributors(ContributorPageReq contributorPageReq) {
        ContributorPageRes contributorPageRes = new ContributorPageRes();
        contributorPageRes.setContributorRes(contributorComponent.findContributors(contributorPageReq));
        return contributorPageRes;
    }

    @Override
    public int getTotalContributorsOrTotalFilteredContributors(ContributorPageReq contributorPageReq) {
        if (contributorPageReq.getUserId() != null
                || contributorPageReq.getFullName() != null
                || contributorPageReq.getPhoneNumber() != null
                || contributorPageReq.getMin() != null
                || contributorPageReq.getMax() != null) {
            return contributorComponent.findTotalContributorsWhenFilter(contributorPageReq);
        }
        return contributorComponent.findTotalContributors();
    }

    @Override
    public int getTheHighestOrLowestTotalVehicleInAllContributors(int option) {
        if (option == 0) {
            return contributorComponent.findTheLowestTotalVehicleInAllContributors();
        }
        return contributorComponent.findTheHighestTotalVehicleInAllContributors();
    }
}
