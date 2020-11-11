package com.fa20se28.vma.service;

import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.response.ContributorDetailRes;
import com.fa20se28.vma.response.ContributorPageRes;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.response.UserPageRes;

public interface ContributorService {
    ContributorDetailRes getContributorById(String userId);

    ContributorPageRes getContributors(ContributorPageReq contributorPageReq);

    int getTotalContributorsOrTotalFilteredContributors(ContributorPageReq contributorPageReq);

    int getTheHighestOrLowestTotalVehicleInAllContributors(int option);


}
