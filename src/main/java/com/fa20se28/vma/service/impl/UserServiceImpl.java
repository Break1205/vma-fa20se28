package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;

    public UserServiceImpl(UserComponent userComponent) {
        this.userComponent = userComponent;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public void updateUserStatusByUserId(Long userStatusId, String userid) {
        userComponent.updateUserStatusByUserId(userStatusId, userid);
    }
}
