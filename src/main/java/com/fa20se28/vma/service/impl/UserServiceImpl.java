package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.model.UserAccount;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.service.FirebaseService;
import com.fa20se28.vma.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;
    private final FirebaseService firebaseService;

    public UserServiceImpl(UserComponent userComponent, FirebaseService firebaseService) {
        this.userComponent = userComponent;
        this.firebaseService = firebaseService;
    }

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
    public void updateUserStatusByUserId(Long userStatusId, String userid) {
        if (userComponent.updateUserStatusByUserId(userStatusId, userid) == 2) {
            User user = userComponent.findUserByUserId(userid);
            UserReq userReq = new UserReq(
                    user.getUserId(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getImageLink());
            firebaseService.createUserRecord(userReq);
        }
    }
}
