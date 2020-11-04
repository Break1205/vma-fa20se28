package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ContributorComponent;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.mapper.ContributorMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.ContributorDetail;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.response.ContributorRes;
import com.fa20se28.vma.response.UserRes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ContributorComponentImpl implements ContributorComponent {
    private final ContributorMapper contributorMapper;
    private final UserDocumentMapper userDocumentMapper;
    private final UserMapper userMapper;

    public ContributorComponentImpl(ContributorMapper contributorMapper, UserDocumentMapper userDocumentMapper, UserMapper userMapper) {
        this.contributorMapper = contributorMapper;
        this.userDocumentMapper = userDocumentMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ContributorDetail findContributorById(String userId) {
        Optional<ContributorDetail> optionalContributorDetail = contributorMapper.findContributorById(userId);
        optionalContributorDetail.ifPresent(contributorDetail ->
                contributorDetail.setUserDocumentList(userDocumentMapper.findUserDocumentByUserId(userId)));
        return optionalContributorDetail.orElseThrow(() ->
                new ResourceNotFoundException("Contributor with id: " + userId + " not found"));
    }

    @Override
    public List<ContributorRes> findContributors(
            ContributorPageReq contributorPageReq) {
        return contributorMapper.findContributorsWhenFilter(contributorPageReq);
    }

    @Override
    public int findTotalContributors() {
        return userMapper.findTotalUserByRoles(2);
    }


    @Override
    public int findTotalContributorsWhenFilter(ContributorPageReq contributorPageReq) {
        return contributorMapper.findTotalContributorsWhenFilter(contributorPageReq);
    }

    @Override
    public int findTheHighestTotalVehicleInAllContributors() {
        return contributorMapper.findTheHighestTotalVehiclesInAllContributors();
    }

    @Override
    public int findTheLowestTotalVehicleInAllContributors() {
        return contributorMapper.findTheLowestTotalVehiclesInAllContributors();
    }

    @Override
    public List<UserRes> findDriversDriveIssuedVehicleOfContributor(String contributorId, UserPageReq userPageReq) {
        return contributorMapper.findDriversDriveIssuedVehicleOfContributor(contributorId, userPageReq);
    }

    @Override
    public int findTotalDriversDriveIssuedVehicleOfContributor(String contributorId, UserPageReq userPageReq) {
        return contributorMapper.findTotalDriversDriveIssuedVehicleOfContributor(contributorId, userPageReq);
    }
}
