package com.fa20se28.vma.component;

import com.fa20se28.vma.model.ContributorDetail;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.response.ContributorRes;
import com.fa20se28.vma.response.UserRes;

import java.util.List;

public interface ContributorComponent {
    ContributorDetail findContributorById(String userId);

    List<ContributorRes> findContributors(ContributorPageReq contributorPageReq);

    int findTotalContributors();

    int findTotalContributorsWhenFilter(ContributorPageReq contributorPageReq);

    int findTheHighestTotalVehicleInAllContributors();

    int findTheLowestTotalVehicleInAllContributors();
}
