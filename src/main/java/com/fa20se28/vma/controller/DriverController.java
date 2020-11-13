package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.IssuedDriversPageReq;
import com.fa20se28.vma.response.DriverDetailRes;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.response.IssuedDriversPageRes;
import com.fa20se28.vma.response.UserPageRes;
import com.fa20se28.vma.service.DriverService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
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

    @GetMapping("/contributors/{contributor-id}")
    public IssuedDriversPageRes getIssuedDrivers(@PathVariable("contributor-id") String contributorId,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String phoneNumber,
                                                 @RequestParam(required = false) String vehicleId,
                                                 @RequestParam(required = false, defaultValue = "0") int page) {
        return driverService.getIssuedDrivers(
                contributorId, new IssuedDriversPageReq(name, phoneNumber, vehicleId, page * 15));
    }

    @GetMapping("/contributors/{contributor-id}/count")
    public int getTotalIssuedDrivers(@PathVariable("contributor-id") String contributorId,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String phoneNumber,
                                     @RequestParam(required = false) String vehicleId) {
        return driverService.getTotalIssuedDrivers(
                contributorId,
                new IssuedDriversPageReq(name, phoneNumber, vehicleId, 0));
    }
}
