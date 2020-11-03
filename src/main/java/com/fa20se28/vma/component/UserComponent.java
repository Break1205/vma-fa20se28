package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.response.UserRes;

import java.util.List;

public interface UserComponent {
    int updateUserStatusByUserId(UserStatus userStatus, String userid);

    User findUserByUserId(String userId);

    List<Role> findUserRoles(String userId);

    int createUserWithRole(UserReq userReq, int roleId);

    int updateUserByUserId(UserReq userReq);

    int deleteUserByUserId(String userId);

    List<UserRes> findUsersWithOneRoleByRoleId(String roleId, UserPageReq userPageReq);

    int findTotalUserWithOneRoleByRoleId(String roleId, UserPageReq userPageReq);
}
