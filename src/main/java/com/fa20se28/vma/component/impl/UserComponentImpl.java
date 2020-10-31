package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.configuration.CustomUtils;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.mapper.UserDocumentImageMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.request.UserDocumentImageReq;
import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.request.UserReq;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class UserComponentImpl implements UserComponent {
    private final UserMapper userMapper;
    private final UserDocumentMapper userDocumentMapper;
    private final UserDocumentImageMapper userDocumentImageMapper;
    private final PasswordEncoder passwordEncoder;

    public UserComponentImpl(UserMapper userMapper,
                             UserDocumentMapper userDocumentMapper,
                             UserDocumentImageMapper userDocumentImageMapper,
                             PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userDocumentMapper = userDocumentMapper;
        this.userDocumentImageMapper = userDocumentImageMapper;
        this.passwordEncoder = passwordEncoder;
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

    @Transactional
    @Override
    public int createUserWithRole(UserReq userReq, int roleId) {
        if (insertUser(userReq, roleId)) {
            return 1;
        }
        return 0;
    }

    @Transactional
    @Override
    public int updateUserByUserId(UserReq userReq) {
        Optional<User> optionalDriverDetail = userMapper.findUserByUserId(userReq.getUserId());
        if (optionalDriverDetail.isPresent()) {
            int driverUpdateSuccess = userMapper.updateUser(userReq);
            if (driverUpdateSuccess >= 0) {
                return 1;
            }
            return 0;
        }
        throw new ResourceNotFoundException("User with id: " + userReq.getUserId() + " not found");
    }

    @Override
    public int deleteUserByUserId(String userId) {
        return userMapper.deleteUserById(userId);
    }

    private boolean insertUser(UserReq userReq, int roleId) {
        String generateId = CustomUtils.randomId();
        Optional<User> optionalDriverDetail = userMapper.findUserByUserId(generateId);
        while (optionalDriverDetail.isPresent()) {
            generateId = CustomUtils.randomId();
            optionalDriverDetail = userMapper.findUserByUserId(generateId);
        }
        int documentRecords = 0;
        int documentImageRecords = 0;
        userReq.setUserId(generateId);
        userReq.setPassword(passwordEncoder.encode(userReq.getPassword()));
        int userRecord = userMapper.insertUser(userReq);
        for (UserDocumentReq userDocumentReq : userReq.getUserDocumentReqList()) {
            documentRecords += userDocumentMapper.insertDocument(userDocumentReq, userReq.getUserId());
            userDocumentMapper.insertDocumentLog(userDocumentReq, userReq.getUserId());
            for (UserDocumentImageReq userDocumentImageReq : userDocumentReq.getUserDocumentImages()) {
                documentImageRecords += userDocumentImageMapper
                        .insertUserDocumentImage(
                                userDocumentImageReq, userDocumentReq.getUserDocumentId());
                userDocumentImageMapper.insertUserDocumentImageLog(userDocumentImageReq, userDocumentReq.getUserDocumentId());
            }
        }
        int userRoles = userMapper.insertRoleForUserId(userReq.getUserId(), roleId);
        return userRecord == 1
                && documentRecords >= 0
                && documentImageRecords >= 0
                && userRoles == 1;
    }

    @Override
    public int updateUserStatusByUserId(UserStatus userStatus, String userId) {
        return userMapper.updateUserStatusByUserId(userStatus, userId);
    }
}
