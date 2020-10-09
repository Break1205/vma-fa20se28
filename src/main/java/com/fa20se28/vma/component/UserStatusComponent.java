package com.fa20se28.vma.component;

import com.fa20se28.vma.model.UserStatus;

import java.util.List;

public interface UserStatusComponent {
    List<UserStatus> getUserStatusList();

    UserStatus findUserStatusById(Long userStatusId);
}
