package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.model.UserStatus;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.mapper.UserStatusMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserComponentImpl implements UserComponent {
    private final UserMapper userMapper;
    private final UserStatusMapper statusMapper;

    public UserComponentImpl(UserMapper userMapper, UserStatusMapper statusMapper) {
        this.userMapper = userMapper;
        this.statusMapper = statusMapper;
    }

    @Override
    public User findDriverById(Long userId) {


        return null;
    }

    @Override
    public List<User> findDrivers() {
        return userMapper.findUsersByRole("driver");
    }

    public List<UserStatus> getStatusList()
    {
        return statusMapper.getStatusList();
    }
}
