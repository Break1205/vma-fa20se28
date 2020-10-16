package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.response.DriverDetailRes;
import com.fa20se28.vma.response.DriverPageRes;
import com.fa20se28.vma.service.DriverService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int createDriver(@RequestBody DriverReq driverReq) throws FirebaseAuthException {
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

    @PutMapping
    public void updateDriver(@RequestBody DriverReq driverReq) throws FirebaseAuthException {
        driverService.updateDriver(driverReq);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(String userId) throws FirebaseAuthException {
        driverService.deleteUserByUserId(userId);
    }
}
