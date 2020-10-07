package com.fa20se28.vma.component;

import com.fa20se28.vma.entity.User;
import com.fa20se28.vma.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserComponent {
    private final UserMapper userMapper;

    public UserComponent(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findUserById(Long userId){
        return userMapper.findUserById(userId);
    }

    public List<User> findUsersByRole(String roleName){
        return userMapper.findUsersByRole(roleName);
    }
}
