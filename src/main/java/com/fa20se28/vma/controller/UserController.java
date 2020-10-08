package com.fa20se28.vma.controller;

import com.fa20se28.vma.response.UserDTO;
import com.fa20se28.vma.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/drivers")
    public List<UserDTO> getDrivers() {
        return userService.getDrivers();
    }

//    @GetMapping("/drivers/{id}")
//    public UserDTO findDriverById(@PathVariable("id") Long userId){
//        return userService.findDriverById(userId);
//    }
}
