package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.ContributorDetail;

import java.util.List;

public interface ContributorComponent {
    ContributorDetail findContributorById(String userId);

    List<Contributor> findContributors(String userId, String name, String phoneNumber, Long totalVehicles, int page);

    int findTotalContributors();

    int findTotalContributorsWhenFilter(String userId, String name, String phoneNumber, Long totalVehicles);

    int findTheHighestTotalVehicleInAllContributors();

    int findTheLowestTotalVehicleInAllContributors();
}
