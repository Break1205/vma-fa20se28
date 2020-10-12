package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ContributorComponent;
import com.fa20se28.vma.mapper.ContributorMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.ContributorDetail;
import org.springframework.stereotype.Component;

import java.util.List;

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
        ContributorDetail contributorDetail = contributorMapper.findContributorById(userId);
        contributorDetail.setUserDocumentList(userDocumentMapper.findUserDocumentByUserId(userId));
        return contributorDetail;
    }

    @Override
    public List<Contributor> findContributors(
            String userId,
            String name,
            String phoneNumber,
            Long userStatusId,
            Long min,
            Long max,
            int page) {
        return contributorMapper.findContributorsByUserIdAndFullNameAndPhoneNumberAndTotalVehicle(
                userId,
                name,
                phoneNumber,
                userStatusId,
                min,
                max,
                page * 15);
    }

    @Override
    public int findTotalContributors() {
        return userMapper.findTotalUserByRoles(2);
    }


    @Override
    public int findTotalContributorsWhenFilter(String userId, String name, String phoneNumber, Long userStatusId, Long min, Long max) {
        return contributorMapper.findTotalContributorsWhenFilter(userId, name, phoneNumber, userStatusId, min, max);
    }

    @Override
    public int findTheHighestTotalVehicleInAllContributors() {
        return contributorMapper.findTheHighestTotalVehiclesInAllContributors();
    }

    @Override
    public int findTheLowestTotalVehicleInAllContributors() {
        return contributorMapper.findTheLowestTotalVehiclesInAllContributors();
    }
}
