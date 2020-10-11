package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ContributorComponent;
import com.fa20se28.vma.model.ContributorDetail;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.response.ContributorDetailRes;
import com.fa20se28.vma.response.ContributorPageRes;
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
        contributorDetailRes.setUserId(contributorDetail.getUserId());
        contributorDetailRes.setUserStatusName(contributorDetail.getUserStatusName());
        contributorDetailRes.setFullName(contributorDetail.getFullName());
        contributorDetailRes.setPhoneNumber(contributorDetail.getPhoneNumber());
        contributorDetailRes.setTotalVehicles(contributorDetail.getTotalVehicles());
        contributorDetailRes.setAddress(contributorDetail.getAddress());
        contributorDetailRes.setBaseSalary(contributorDetail.getBaseSalary());
        contributorDetailRes.setDateOfBirth(contributorDetail.getDateOfBirth());
        contributorDetailRes.setGender(contributorDetail.isGender());
        contributorDetailRes.setImageLink(contributorDetail.getImageLink());
        contributorDetailRes.setUserDocumentList(contributorDetail.getUserDocumentList());
        return contributorDetailRes;
    }

    @Override
    public ContributorPageRes getContributors(ContributorPageReq contributorPageReq) {
        ContributorPageRes contributorPageRes = new ContributorPageRes();
        contributorPageRes.setContributorList(contributorComponent
                .findContributors(
                        contributorPageReq.getUserId(),
                        contributorPageReq.getName(),
                        contributorPageReq.getPhoneNumber(),
                        contributorPageReq.getTotalVehicles(),
                        contributorPageReq.getPage()));
        return contributorPageRes;
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

    @Override
    public int getTheHighestOrLowestTotalVehicleInAllContributors(int option) {
        if (option == 0) {
            return contributorComponent.findTheLowestTotalVehicleInAllContributors();
        }
        return contributorComponent.findTheHighestTotalVehicleInAllContributors();
    }

    private int getTotalContributors() {
        return contributorComponent.findTotalContributors();
    }

    private int getTotalContributorsWhenFiltering(ContributorPageReq contributorPageReq) {
        return contributorComponent
                .findTotalContributorsWhenFilter(
                        contributorPageReq.getUserId(),
                        contributorPageReq.getName(),
                        contributorPageReq.getPhoneNumber(),
                        contributorPageReq.getTotalVehicles());
    }
}
