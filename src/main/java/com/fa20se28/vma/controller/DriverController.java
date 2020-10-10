package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.service.DriverService;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping()
    public DriverPageRes getDrivers(@RequestParam(required = false) String userId,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String phoneNumber,
                                    @RequestParam(required = false) Long userStatusId,
                                    @RequestParam(required = false, defaultValue = "0") int page) {
        return driverService.getDrivers(new DriverPageReq(userId, name, phoneNumber, userStatusId, page));
    }

    @GetMapping("count")
    public int getTotalDrivers(@RequestParam(required = false) String userId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String phoneNumber,
                               @RequestParam(required = false) Long userStatusId) {
        return driverService.getTotalDriversOrTotalFilteredDriver(new DriverPageReq(userId, name, phoneNumber, userStatusId));
    }
}
