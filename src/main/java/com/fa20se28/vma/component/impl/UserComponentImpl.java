package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.mapper.RequestMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.Request;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserComponentImpl implements UserComponent {
    private final UserMapper userMapper;
    private final RequestMapper requestMapper;

    public UserComponentImpl(UserMapper userMapper, RequestMapper requestMapper) {
        this.userMapper = userMapper;
        this.requestMapper = requestMapper;
    }


    @Override
    public User findUserByUserId(String userId) {
        Optional<User> optionalUser = userMapper.findUserByUserId(userId);
        return optionalUser.orElseThrow(()
                -> new ResourceNotFoundException("User with id: " + userId + " not found"));
    }

    @Override
    public List<Role> findUserRoles(String userId) {
        return userMapper.findUserRoles(userId);
    }

    // TODO
    @Override
    public int updateUserStatusByUserId(Long userStatusId, String userId) {
        return -1;
    }
}
