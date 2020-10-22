package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;

import java.util.List;

public interface UserComponent {
    int updateUserStatusByUserId(Long userStatusId, String userid);

    User findUserByUserId(String userId);

    List<Role> findUserRoles(String userId);
}
