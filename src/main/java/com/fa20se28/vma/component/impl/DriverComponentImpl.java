package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.DriverComponent;
import com.fa20se28.vma.configuration.CustomUtils;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.mapper.UserDocumentImageMapper;
import com.fa20se28.vma.mapper.DriverMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.request.UserDocumentImageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.response.DriverRes;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class DriverComponentImpl implements DriverComponent {
    private final UserMapper userMapper;
    private final DriverMapper driverMapper;
    private final UserDocumentMapper userDocumentMapper;
    private final UserDocumentImageMapper userDocumentImageMapper;
    private final PasswordEncoder passwordEncoder;

    public DriverComponentImpl(DriverMapper driverMapper,
                               UserDocumentMapper userDocumentMapper,
                               UserDocumentImageMapper userDocumentImageMapper,
                               UserMapper userMapper,
                               PasswordEncoder passwordEncoder) {
        this.driverMapper = driverMapper;
        this.userDocumentMapper = userDocumentMapper;
        this.userDocumentImageMapper = userDocumentImageMapper;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public int createDriver(DriverReq driverReq) {
        if (insertDriver(driverReq)) {
            return 1;
        }
        return 0;
    }

    private boolean insertDriver(DriverReq driverReq) {
        String generateId = CustomUtils.randomId();
        Optional<DriverDetail> optionalDriverDetail = driverMapper.findDriverById(generateId);
        while (optionalDriverDetail.isPresent()) {
            generateId = CustomUtils.randomId();
            optionalDriverDetail = driverMapper.findDriverById(generateId);
        }
        int documentRecords = 0;
        int documentImageRecords = 0;
        driverReq.setUserId(generateId);
        driverReq.setPassword(passwordEncoder.encode(driverReq.getPassword()));
        int driverRecord = driverMapper.insertDriver(driverReq);
        for (UserDocumentReq userDocumentReq : driverReq.getUserDocumentReqList()) {
            documentRecords += userDocumentMapper.insertDocument(userDocumentReq, driverReq.getUserId());
            for (UserDocumentImageReq userDocumentImageReq : userDocumentReq.getDocumentImagesReqList()) {
                documentImageRecords += userDocumentImageMapper
                        .insertUserDocumentImage(
                                userDocumentImageReq, userDocumentReq.getUserDocumentId());
            }
        }
        int userRoles = userMapper.insertRoleForUserId(driverReq.getUserId(), 3);
        return driverRecord == 1
                && documentRecords >= 0
                && documentImageRecords >= 0
                && userRoles == 1;
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
    public int deleteDriverById(String userId) {
        return userMapper.deleteUserById(userId);
    }

    @Transactional
    @Override
    public int updateDriverByUserId(DriverReq driverReq) {
        Optional<DriverDetail> optionalDriverDetail = driverMapper.findDriverById(driverReq.getUserId());
        if (optionalDriverDetail.isPresent()) {
            int driverUpdateSuccess = driverMapper.updateDriver(driverReq);
            if (driverUpdateSuccess >= 0) {
                return 1;
            }
            return 0;
        }
        throw new ResourceNotFoundException("Driver with id: " + driverReq.getUserId() + " not found");
    }
}
