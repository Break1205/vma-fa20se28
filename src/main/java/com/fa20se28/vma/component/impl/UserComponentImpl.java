package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.response.DriverPageRes;
import org.springframework.stereotype.Component;

@Component
public class UserComponentImpl implements UserComponent {
    private final UserMapper userMapper;

    public UserComponentImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findDriverById(Long userId) {
        return userMapper.findUserById(userId);
    }

    @Override
    public DriverPageRes findDrivers(String userId, String name, String phoneNumber, Long userStatusId, int page) {
        DriverPageRes driverPageRes = new DriverPageRes();
        driverPageRes.setDriverList(userMapper.findDriversByUserIdAndFullNameAndPhoneNumberAndUserStatus(userId,
                name, phoneNumber, userStatusId, page));
        driverPageRes.setTotalDrivers(userMapper.findNumberOfDrivers());
        return driverPageRes;
    }
}
