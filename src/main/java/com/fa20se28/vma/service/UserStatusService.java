package com.fa20se28.vma.service;

import com.fa20se28.vma.model.UserStatus;

import java.util.List;

public interface UserStatusService {
    List<UserStatus> getUserStatusList();

    UserStatus getUserStatusById(Long userStatusId);
}
