package com.fa20se28.vma.controller;

import com.fa20se28.vma.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PatchMapping("/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserStatusByUserId(@RequestParam Long userStatusId,
                                         @RequestParam
                                         @PathVariable("user-id") String userid) {
        userService.updateUserStatusByUserId(userStatusId, userid);
    }
}
