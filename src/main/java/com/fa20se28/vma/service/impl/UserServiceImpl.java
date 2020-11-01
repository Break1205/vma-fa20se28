package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.model.UserAccount;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.service.FirebaseService;
import com.fa20se28.vma.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;
    private final FirebaseService firebaseService;

    public UserServiceImpl(UserComponent userComponent, FirebaseService firebaseService) {
        this.userComponent = userComponent;
        this.firebaseService = firebaseService;
    }

    //TODO
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
    public int createUser(UserReq userReq, int roleId) {
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
}
