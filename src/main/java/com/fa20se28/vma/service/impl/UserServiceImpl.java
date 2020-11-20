package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.AuthenticationComponent;
import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.configuration.exception.InvalidParamException;
import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.model.ClientRegistrationToken;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.model.UserAccount;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.request.UserTokenReq;
import com.fa20se28.vma.response.UserPageRes;
import com.fa20se28.vma.response.UserRoleRes;
import com.fa20se28.vma.service.FirebaseService;
import com.fa20se28.vma.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;
    private final FirebaseService firebaseService;
    private final AuthenticationComponent authenticationComponent;

    public UserServiceImpl(UserComponent userComponent, FirebaseService firebaseService, AuthenticationComponent authenticationComponent) {
        this.userComponent = userComponent;
        this.firebaseService = firebaseService;
        this.authenticationComponent = authenticationComponent;
    }

    //TODO password
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userComponent.findUserByUserId(userId);
        List<Role> userRoles = userComponent.findUserRoles(userId);
        if (user != null && userRoles != null) {
            return new UserAccount(user, userRoles);
        }
        return null;
    }

    @Override
    public void updateUserStatusByUserId(UserStatus userStatus, String userid) {
        userComponent.updateUserStatusByUserId(userStatus, userid);
    }

    @Override
    @Transactional
    public int createUser(UserReq userReq, Long roleId) {
        int success = userComponent.createUserWithRole(userReq, roleId);
        if (success == 1) {
            firebaseService.createUserRecord(userReq);
            return 1;
        }
        return 0;
    }

    @Override
    public void updateUser(UserReq userReq) {
        if (userComponent.updateUserByUserId(userReq) == 1) {
            firebaseService.updateUserRecord(userReq);
        }
    }

    @Override
    public void deleteUserByUserId(String userId) {
        if (userComponent.deleteUserByUserId(userId) == 1) {
            firebaseService.deleteUserRecord(userId);
        }
    }

    @Override
    public UserRoleRes getUserRoles(UserTokenReq token) {
        return new UserRoleRes(userComponent.findUserRoles(firebaseService.decodeToken(token)));
    }

    @Override
    public UserPageRes getUsersWithOneRoleByRoleId(String roleId, UserPageReq userPageReq) {
        UserPageRes userPageRes = new UserPageRes();
        userPageRes.setUserRes(userComponent.findUsersWithOneRoleByRoleId(roleId, userPageReq));
        return userPageRes;
    }

    @Override
    public int getTotalUserWithOneRoleByRoleId(String roleId, UserPageReq userPageReq) {
        return userComponent.findTotalUserWithOneRoleByRoleId(roleId, userPageReq);
    }

    @Override
    public void addNewRoleForUser(Long roleId, String userId) {
        List<Role> userRoles = userComponent.findUserRoles(userId);
        for (Role existedRole : userRoles) {
            if (existedRole.getRoleId().equals(roleId)) {
                throw new InvalidParamException("User with id: " + userId + " already has role with id " + roleId);
            }
        }
        userComponent.addNewRoleForUser(roleId, userId);
    }

    @Override
    public int updateClientRegistrationToken(ClientRegistrationToken clientRegistrationToken) {
        Authentication authentication = authenticationComponent.getAuthentication();
        return userComponent.updateClientRegistrationToken(clientRegistrationToken, authentication.getName());
    }
}
