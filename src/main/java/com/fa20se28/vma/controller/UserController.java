package com.fa20se28.vma.controller;

import com.fa20se28.vma.entity.User;
import com.fa20se28.vma.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{user-id}")
    public User findUserById(@PathVariable(value = "user-id") Long userId){
        return userService.findUserById(userId);
    }

    @GetMapping("/users")
    public List<User> findUsersByRole(@RequestParam String roleName) {
        return userService.findUsersByRole(roleName);
    }
}
