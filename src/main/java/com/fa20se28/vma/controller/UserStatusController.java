package com.fa20se28.vma.controller;

import com.fa20se28.vma.model.UserStatus;
import com.fa20se28.vma.service.UserStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserStatusController {
    private final UserStatusService userStatusService;

    public UserStatusController(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    @GetMapping("/user-status")
    public List<UserStatus> getUserStatuses(){
        return userStatusService.getUserStatusList();
    }

    @GetMapping("/user-status/{user-status-id}")
    public UserStatus getUserStatusById(@PathVariable("user-status-id") Long userStatusId){
        return userStatusService.getUserStatusById(userStatusId);
    }
}
