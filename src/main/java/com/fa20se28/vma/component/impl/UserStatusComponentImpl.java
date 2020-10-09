package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserStatusComponent;
import com.fa20se28.vma.mapper.UserStatusMapper;
import com.fa20se28.vma.model.UserStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserStatusComponentImpl implements UserStatusComponent {
    private final UserStatusMapper userStatusMapper;

    public UserStatusComponentImpl(UserStatusMapper userStatusMapper) {
        this.userStatusMapper = userStatusMapper;
    }

    @Override
    public List<UserStatus> getUserStatusList() {
        return userStatusMapper.getStatusList();
    }

    @Override
    public UserStatus findUserStatusById(Long userStatusId) {
        return userStatusMapper.findStatusById(userStatusId);
    }
}
