package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/drivers")
    public DriverPageRes getDrivers(@RequestParam(required = false) String userId,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String phoneNumber,
                                    @RequestParam(required = false) Long userStatusId,
                                    @RequestParam(required = false, defaultValue = "0") int page) {
        return userService.getDrivers(new DriverPageReq(userId,name,phoneNumber,userStatusId,page));
    }

//    @GetMapping("/drivers/{id}")
//    public UserDTO findDriverById(@PathVariable("id") Long userId){
//        return userService.findDriverById(userId);
//    }
}
