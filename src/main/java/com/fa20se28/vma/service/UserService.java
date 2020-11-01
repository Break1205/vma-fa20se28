package com.fa20se28.vma.service;

import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.request.UserTokenReq;
import com.fa20se28.vma.response.UserRoleRes;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void updateUserStatusByUserId(UserStatus userStatus, String userid);

    int createUser(UserReq userReq, int roleId);

    void updateUser(UserReq userReq);

    void deleteUserByUserId(String userId);

    UserRoleRes getUserRoles(UserTokenReq token);
}
