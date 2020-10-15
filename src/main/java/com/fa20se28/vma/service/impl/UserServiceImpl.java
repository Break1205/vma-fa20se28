package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.model.UserAccount;
import com.fa20se28.vma.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;

    public UserServiceImpl(UserComponent userComponent) {
        this.userComponent = userComponent;
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
        userComponent.updateUserStatusByUserId(userStatusId, userid);
    }
}
