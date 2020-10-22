package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.DriverComponent;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.mapper.DocumentImageMapper;
import com.fa20se28.vma.mapper.DriverMapper;
import com.fa20se28.vma.mapper.RequestMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.model.Request;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.request.DocumentImageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.response.DriverRes;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class DriverComponentImpl implements DriverComponent {
    private final UserMapper userMapper;
    private final DriverMapper driverMapper;
    private final UserDocumentMapper userDocumentMapper;
    private final DocumentImageMapper documentImageMapper;
    private final RequestMapper requestMapper;

    public DriverComponentImpl(DriverMapper driverMapper,
                               UserDocumentMapper userDocumentMapper,
                               DocumentImageMapper documentImageMapper,
                               UserMapper userMapper,
                               RequestMapper requestMapper) {
        this.driverMapper = driverMapper;
        this.userDocumentMapper = userDocumentMapper;
        this.documentImageMapper = documentImageMapper;
        this.userMapper = userMapper;
        this.requestMapper = requestMapper;
    }

    @Override
    @Transactional
    public int createDriver(DriverReq driverReq) {
        if (checkUserIdValidity(driverReq.getUserId())) {
            int documentRecords = 0;
            int documentImageRecords = 0;
            int driverRecord = driverMapper.insertDriver(driverReq);
            for (UserDocumentReq userDocumentReq : driverReq.getUserDocumentReqList()) {
                userDocumentMapper.insertDocument(userDocumentReq, driverReq.getUserId());
                documentRecords++;
                for (DocumentImageReq documentImageReq : userDocumentReq.getDocumentImagesReqList()) {
                    documentImageMapper.insertDocumentImage(documentImageReq, userDocumentReq.getUserDocumentId());
                    documentImageRecords++;
                }
            }
            int userRoles = userMapper.insertRoleForUserId(driverReq.getUserId(), 3);
            int requestRecords = requestMapper.insertRequest(
                    new Request(
                            driverReq.getUserId(),
                            1L,
                            "New Registration",
                            false));
            if (driverRecord == 1
                    && requestRecords == 1
                    && documentRecords > 0
                    && documentImageRecords > 0
                    && userRoles == 1) {
                return 1;
            }
        }
        return 0;
    }

    private boolean checkUserIdValidity(String userId) {
        Optional<User> optionalUser = userMapper.findUserByUserId(userId);
        if (optionalUser.isPresent()) {
            return false;
        }else{
            return true;
        }
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
    public void deleteDriverById(String userId) {
        userMapper.deleteUserById(userId);
    }

    @Transactional
    @Override
    public void updateDriverByUserId(DriverReq driverReq) {
        driverMapper.updateDriver(driverReq);
        for (UserDocumentReq userDocumentReq : driverReq.getUserDocumentReqList()) {
            userDocumentMapper.updateDocument(userDocumentReq, driverReq.getUserId());
            for (DocumentImageReq documentImageReq : userDocumentReq.getDocumentImagesReqList()) {
                documentImageMapper.updateDocumentImage(documentImageReq, userDocumentReq.getUserDocumentId());
            }
        }
    }

}
