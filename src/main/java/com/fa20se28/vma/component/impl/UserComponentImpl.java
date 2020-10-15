package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserComponentImpl implements UserComponent {
    private final UserMapper userMapper;

    public UserComponentImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public User findUserByUserId(String userId) {
        return userMapper.findUserByUserId(userId);
    }

    @Override
    public List<Role> findUserRoles(String userId) {
        return userMapper.findUserRoles(userId);
    }

    @Override
    public void updateUserStatusByUserId(Long userStatusId, String userid) {
        userMapper.updateUserStatusByUserId(userStatusId, userid);
    }
}
