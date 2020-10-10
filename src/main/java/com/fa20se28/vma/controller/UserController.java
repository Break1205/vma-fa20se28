package com.fa20se28.vma.controller;

import com.fa20se28.vma.model.Contributor;
import com.fa20se28.vma.model.Driver;
import com.fa20se28.vma.request.ContributorPageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/drivers")
    public List<Driver> getDrivers(@RequestParam(required = false) String userId,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String phoneNumber,
                                   @RequestParam(required = false) Long userStatusId,
                                   @RequestParam(required = false, defaultValue = "0") int page) {
        return userService.getDrivers(new DriverPageReq(userId, name, phoneNumber, userStatusId, page));
    }

    @GetMapping("/contributors")
    public List<Contributor> getContributors(@RequestParam(required = false) String userId,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) String phoneNumber,
                                             @RequestParam(required = false) Long totalVehicle,
                                             @RequestParam(required = false, defaultValue = "0") int page) {
        return userService.getContributors(new ContributorPageReq(userId, name, phoneNumber, totalVehicle, page));
    }

    @GetMapping("/drivers/count")
    public int getTotalDrivers(@RequestParam(required = false) String userId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String phoneNumber,
                               @RequestParam(required = false) Long userStatusId){
        return userService.getTotalDriversOrTotalFilteredDriver(new DriverPageReq(userId, name, phoneNumber, userStatusId));
    }

    @GetMapping("/contributors/count")
    public int getTotalContributor(@RequestParam(required = false) String userId,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String phoneNumber,
                                   @RequestParam(required = false) Long totalVehicle){
        return userService.getTotalContributorsOrTotalFilteredContributors(new ContributorPageReq(userId, name, phoneNumber, totalVehicle));
    }

//    @GetMapping("/drivers/{id}")
//    public UserDTO findDriverById(@PathVariable("id") Long userId){
//        return userService.findDriverById(userId);
//    }
}
