package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.DriverComponent;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.mapper.DriverMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.IssuedDriversPageReq;
import com.fa20se28.vma.response.DriverRes;
import com.fa20se28.vma.response.IssuedDriversRes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DriverComponentImpl implements DriverComponent {
    private final UserMapper userMapper;
    private final DriverMapper driverMapper;
    private final UserDocumentMapper userDocumentMapper;

    public DriverComponentImpl(UserMapper userMapper, DriverMapper driverMapper, UserDocumentMapper userDocumentMapper) {
        this.userMapper = userMapper;
        this.driverMapper = driverMapper;
        this.userDocumentMapper = userDocumentMapper;
    }

    @Override
    public DriverDetail findDriverById(String userId) {
        Optional<DriverDetail> optionalDriverDetail = driverMapper.findDriverById(userId);
        optionalDriverDetail.ifPresent(detail ->
                detail.
                        setUserDocumentList(userDocumentMapper.
                                findUserDocumentByUserId(userId, 0)));
        return optionalDriverDetail.orElseThrow(() ->
                new ResourceNotFoundException("Driver with id: " + userId + " not found"));
    }

    @Override
    public List<DriverRes> findDrivers(DriverPageReq driverPageReq) {
        return driverMapper.findDrivers(driverPageReq);
    }

    @Override
    public int findTotalDrivers() {
        return userMapper.findTotalUserByRoles(3);
    }

    @Override
    public int findTotalDriversWhenFiltering(DriverPageReq driverPageReq) {
        return driverMapper.findTotalDriversWhenFilter(driverPageReq);
    }

    @Override
    public List<IssuedDriversRes> findIssuedDrivers(String contributorId, IssuedDriversPageReq issuedDriversPageReq) {
        return driverMapper.findIssuedDrivers(contributorId, issuedDriversPageReq);
    }

    @Override
    public int findTotalIssuedDrivers(String contributorId, IssuedDriversPageReq issuedDriversPageReq) {
        return driverMapper.findTotalIssuedDrivers(contributorId, issuedDriversPageReq);
    }
}
