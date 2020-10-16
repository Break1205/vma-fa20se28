package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.DriverComponent;
import com.fa20se28.vma.mapper.DriverMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.DocumentImageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserDocumentReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            userDocumentReq.setUserId(driverReq.getUserId());
            userMapper.insertDocument(userDocumentReq);
            documentRecords++;
            for (DocumentImageReq documentImageReq : userDocumentReq.getDocumentImagesReqList()) {
                documentImageReq.setDocumentId(userDocumentReq.getUserDocumentId());
                userMapper.insertDocumentImage(documentImageReq);
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
        DriverDetail driverDetail = driverMapper.findDriverById(userId);
        driverDetail.setUserDocumentList(userDocumentMapper.findUserDocumentByUserId(userId));
        return driverDetail;
    }

    @Override
    public List<Driver> findDrivers(String userId, String name, String phoneNumber, Long userStatusId, int page) {
        return driverMapper
                .findDriversByUserIdAndFullNameAndPhoneNumberAndUserStatus(
                        userId,
                        name,
                        phoneNumber,
                        userStatusId,
                        page * 15);
    }

    @Override
    public int findTotalDrivers() {
        return userMapper.findTotalUserByRoles(3);
    }

    @Override
    public int findTotalDriversWhenFilter(String userId, String name, String phoneNumber, Long userStatusId) {
        return driverMapper.findTotalDriversWhenFilter(userId, name, phoneNumber, userStatusId);
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
            userDocumentReq.setUserId(driverReq.getUserId());
            userMapper.updateDocument(userDocumentReq);
            for (DocumentImageReq documentImageReq : userDocumentReq.getDocumentImagesReqList()) {
                documentImageReq.setDocumentId(userDocumentReq.getUserDocumentId());
                userMapper.updateDocumentImage(documentImageReq);
            }
        }
    }

}
