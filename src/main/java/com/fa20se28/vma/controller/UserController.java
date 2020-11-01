package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.request.UserTokenReq;
import com.fa20se28.vma.response.UserRoleRes;
import com.fa20se28.vma.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int createUser(@RequestBody UserReq userReq,
                          @RequestParam int roleId) {
        return userService.createUser(userReq, roleId);
    }

    @PutMapping
    public void updateUser(@RequestBody UserReq userReq) {
        userService.updateUser(userReq);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(String userId) {
        userService.deleteUserByUserId(userId);
    }

    @PatchMapping("/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserStatusByUserId(@RequestParam UserStatus userStatus,
                                         @PathVariable("user-id") String userid) {
        userService.updateUserStatusByUserId(userStatus, userid);
    }

    @PostMapping("/role-token")
    public UserRoleRes getUserRoles(@RequestBody UserTokenReq token) {
        return userService.getUserRoles(token);
    }
}
