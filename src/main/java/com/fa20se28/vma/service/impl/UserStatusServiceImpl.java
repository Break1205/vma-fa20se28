package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.UserStatusComponent;
import com.fa20se28.vma.model.UserStatus;
import com.fa20se28.vma.service.UserStatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatusServiceImpl implements UserStatusService {
    private final UserStatusComponent userStatusComponent;

    public UserStatusServiceImpl(UserStatusComponent userStatusComponent) {
        this.userStatusComponent = userStatusComponent;
    }

    @Override
    public List<UserStatus> getUserStatusList() {
        return userStatusComponent.getUserStatusList();
    }

    @Override
    public UserStatus getUserStatusById(Long userStatusId) {
        return userStatusComponent.findUserStatusById(userStatusId);
    }
}
