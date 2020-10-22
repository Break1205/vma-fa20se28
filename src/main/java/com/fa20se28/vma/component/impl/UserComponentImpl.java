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

    @Override
    public int updateUserStatusByUserId(Long userStatusId, String userId) {
        int result = 0;
        Optional<User> optionalUser = userMapper.findUserByUserId(userId);
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getUserStatusId() == 3) {
                Optional<Request> optionalRequest = requestMapper.findRequestByUserId(userId, 4L);
                if (optionalRequest.isPresent()) {
                    if (userStatusId == 2) { // UserStatus: 2 Inactive -> RequestStatus: 2 Accepted
                        requestMapper.updateRequestStatus(optionalRequest.get().getRequestId(), 2L);
                        userMapper.updateUserStatusByUserId(userStatusId, userId);
                        result = 2;
                    }
                    if (userStatusId == 4) { // UserStatus: 4 Disable -> RequestStatus; 3 Denied
                        requestMapper.updateRequestStatus(optionalRequest.get().getRequestId(), 3L);
                        userMapper.updateUserStatusByUserId(userStatusId, userId);
                        result = 3;
                    }
                }
            } else {
                userMapper.updateUserStatusByUserId(userStatusId, userId);
                result = 1;
            }
        }
        return result;
    }
}
