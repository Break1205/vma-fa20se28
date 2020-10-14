package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserComponentImpl implements UserComponent {
    private final UserMapper userMapper;

    public UserComponentImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public void updateUserStatusByUserId(Long userStatusId, String userid) {
        userMapper.updateUserStatusByUserId(userStatusId, userid);
    }
}
