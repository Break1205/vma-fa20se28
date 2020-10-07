package com.fa20se28.vma.service;

import com.fa20se28.vma.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findUserById(Long userId);

    List<User> findUsersByRole(String roleName);
}
