package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.DriverComponent;
import com.fa20se28.vma.mapper.DriverMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.DriverDetail;
import org.springframework.stereotype.Component;

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
}
