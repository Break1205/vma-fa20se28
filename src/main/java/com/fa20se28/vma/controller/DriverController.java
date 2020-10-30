package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.response.DriverDetailRes;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.response.DriverRes;
import com.fa20se28.vma.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int createDriver(@RequestBody DriverReq driverReq) {
        return driverService.createDriver(driverReq);
    }

    @GetMapping("/{driver-id}")
    public DriverDetailRes getDriverById(@PathVariable("driver-id") String userId) {
        return driverService.getDriverById(userId);
    }

    @GetMapping()
    public DriverPageRes getDrivers(@RequestParam(required = false) String userId,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String phoneNumber,
                                    @RequestParam(required = false) UserStatus userStatus,
                                    @RequestParam(required = false, defaultValue = "0") int page) {
        return driverService.getDrivers(new DriverPageReq(userId, name, phoneNumber, userStatus, page * 15));
    }

    @GetMapping("count")
    public int getTotalDrivers(@RequestParam(required = false) String userId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String phoneNumber,
                               @RequestParam(required = false) UserStatus userStatus) {
        return driverService.getTotalDriversOrTotalFilteredDriver(
                new DriverPageReq(
                        userId, name, phoneNumber, userStatus, 0));
    }

    @PutMapping
    public void updateDriver(@RequestBody DriverReq driverReq) {
        driverService.updateDriver(driverReq);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(String userId) {
        driverService.deleteUserByUserId(userId);
    }
}
