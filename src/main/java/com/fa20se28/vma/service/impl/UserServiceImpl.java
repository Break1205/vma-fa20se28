package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.entity.User;
import com.fa20se28.vma.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;

    public UserServiceImpl(UserComponent userComponent) {
        this.userComponent = userComponent;
    }

    public User findUserById(Long userId){
        return userComponent.findUserById(userId);
    }

    @Override
    public List<User> findUsersByRole(String roleName) {
        return userComponent.findUsersByRole(roleName);
    }
}
