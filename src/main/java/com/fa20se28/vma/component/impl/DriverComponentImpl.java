package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.DriverComponent;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.mapper.DriverMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DocumentImageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserDocumentReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class DriverComponentImpl implements DriverComponent {
    private final DriverMapper driverMapper;
    private final UserDocumentMapper userDocumentMapper;
    private final UserMapper userMapper;

    public DriverComponentImpl(DriverMapper driverMapper, UserDocumentMapper userDocumentMapper, UserMapper userMapper) {
        this.driverMapper = driverMapper;
        this.userDocumentMapper = userDocumentMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public int createDriver(DriverReq driverReq) {
        int documentRecords = 0;
        int documentImageRecords = 0;
        int driverRecord = userMapper.insertDriver(driverReq);
        for (UserDocumentReq userDocumentReq : driverReq.getUserDocumentReqList()) {
            userMapper.insertDocument(userDocumentReq, driverReq.getUserId());
            documentRecords++;
            for (DocumentImageReq documentImageReq : userDocumentReq.getDocumentImagesReqList()) {
                userMapper.insertDocumentImage(documentImageReq, userDocumentReq.getUserDocumentId());
                documentImageRecords++;
            }
        }
        int userRoles = userMapper.insertRoleForUserId(driverReq.getUserId(), 3);
        if (driverRecord == 1
                && documentRecords > 0
                && documentImageRecords > 0
                && userRoles == 1) {
            return 1;
        }
        return 0;
    }

    @Override
    public DriverDetail findDriverById(String userId) {
        Optional<DriverDetail> optionalDriverDetail = driverMapper.findDriverById(userId);
        optionalDriverDetail.ifPresent(detail ->
                detail.
                        setUserDocumentList(userDocumentMapper.
                                findUserDocumentByUserId(userId)));
        return optionalDriverDetail.orElseThrow(() ->
                new ResourceNotFoundException("Driver with id: " + userId + " not found"));
    }

    @Override
    public List<Driver> findDrivers(DriverPageReq driverPageReq) {
        return driverMapper
                .findDriversByUserIdAndFullNameAndPhoneNumberAndUserStatus(driverPageReq);
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
    public void deleteDriverById(String userId) {
        userMapper.deleteUserById(userId);
    }

    @Transactional
    @Override
    public void updateDriverByUserId(DriverReq driverReq) {
        userMapper.updateDriver(driverReq);
        for (UserDocumentReq userDocumentReq : driverReq.getUserDocumentReqList()) {
            userMapper.updateDocument(userDocumentReq, driverReq.getUserId());
            for (DocumentImageReq documentImageReq : userDocumentReq.getDocumentImagesReqList()) {
                userMapper.updateDocumentImage(documentImageReq, userDocumentReq.getUserDocumentId());
            }
        }
    }

}
