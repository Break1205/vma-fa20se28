package com.fa20se28.vma.service;

import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.model.ClientRegistrationToken;
import com.fa20se28.vma.request.JwtReq;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.request.UserTokenReq;
import com.fa20se28.vma.response.UserPageRes;
import com.fa20se28.vma.response.UserRoleRes;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByPhoneNumberAndPassword(JwtReq jwtRequest);

    void updateUserStatusByUserId(UserStatus userStatus, String userid);

    int createUser(UserReq userReq, Long roleId);

    void updateUser(UserReq userReq);

    void deleteUserByUserId(String userId);

    UserRoleRes getUserRoles(UserTokenReq token);

    UserPageRes getUsersWithOneRoleByRoleId(String roleId, UserPageReq userPageReq);

    int getTotalUserWithOneRoleByRoleId(String roleId, UserPageReq userPageReq);

    void addNewRoleForUser(Long roleId, String userid);

    int updateClientRegistrationToken(ClientRegistrationToken clientRegistrationToken);

    void changePassword(String userId, String password);
}
