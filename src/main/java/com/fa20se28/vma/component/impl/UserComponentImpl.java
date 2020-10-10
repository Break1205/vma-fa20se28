package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public List<Driver> findDrivers(String userId, String name, String phoneNumber, Long userStatusId, int page) {
        return userMapper
                .findDriversByUserIdAndFullNameAndPhoneNumberAndUserStatus(
                        userId,
                        name,
                        phoneNumber,
                        userStatusId,
                        page*15);
    }

    @Override
    public int findTotalDrivers() {
        return userMapper.findTotalUserByRoles(3);
    }

    @Override
    public int findTotalDriversWhenFilter(String userId, String name, String phoneNumber, Long userStatusId) {
        return userMapper.findTotalDriversWhenFilter(userId, name, phoneNumber, userStatusId);
    }

    @Override
    public List<Contributor> findContributors(String userId, String name, String phoneNumber, Long totalVehicles, int page) {
        return userMapper.findContributorsByUserIdAndFullNameAndPhoneNumberAndTotalVehicle(
                userId,
                name,
                phoneNumber,
                totalVehicles,
                page*15);
    }

    @Override
    public int findTotalContributors() {
        return userMapper.findTotalUserByRoles(2);
    }


    @Override
    public int findTotalContributorsWhenFilter(String userId, String name, String phoneNumber, Long totalVehicles) {
        return userMapper.findTotalContributorsWhenFilter(userId, name, phoneNumber, totalVehicles);
    }
}
