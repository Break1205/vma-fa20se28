package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.MaintenanceType;
import com.fa20se28.vma.request.MaintenancePageReq;
import com.fa20se28.vma.request.MaintenanceReq;
import com.fa20se28.vma.request.MaintenanceUpdateReq;
import com.fa20se28.vma.response.MaintenanceDetailRes;
import com.fa20se28.vma.response.MaintenancePageRes;
import com.fa20se28.vma.response.MaintenanceTypeRes;
import com.fa20se28.vma.service.MaintenanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/maintenances")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMaintenanceForVehicle(@RequestBody MaintenanceReq maintenanceReq) {
        maintenanceService.createMaintenance(maintenanceReq);
    }

    @PatchMapping
    public void updateMaintenanceForVehicle(@RequestBody MaintenanceUpdateReq maintenanceUpdateReq) {
        maintenanceService.updateMaintenance(maintenanceUpdateReq);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaintenanceForVehicle(@RequestParam int maintenanceId) {
        maintenanceService.deleteMaintenance(maintenanceId);
    }

    @GetMapping("/count")
    public int getTotalMaintenance(
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) MaintenanceType maintenanceType,
            @RequestParam(required = false, defaultValue = "0") float costMin,
            @RequestParam(required = false, defaultValue = "0") float costMax,
            @RequestParam(required = false, defaultValue = "0") int viewOption) {
        return maintenanceService.getTotalMaintenance(
                new MaintenancePageReq(vehicleId, startDate, endDate, maintenanceType, costMin, costMax),
                viewOption);
    }

    @GetMapping
    public MaintenancePageRes getMaintenances(
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) MaintenanceType maintenanceType,
            @RequestParam(required = false, defaultValue = "0") float costMin,
            @RequestParam(required = false, defaultValue = "0") float costMax,
            @RequestParam(required = false, defaultValue = "0") int viewOption,
            @RequestParam(required = false, defaultValue = "0") int pageNum) {
        return maintenanceService.getMaintenances(
                new MaintenancePageReq(vehicleId, startDate, endDate, maintenanceType, costMin, costMax),
                viewOption,
                pageNum);
    }

    @GetMapping("/{maintenance-id}")
    public MaintenanceDetailRes getMaintenanceDetailByMaintenanceId(@PathVariable("maintenance-id") int maintenanceId) {
        return maintenanceService.getMaintenanceDetail(maintenanceId);
    }

    @GetMapping("/types")
    public MaintenanceTypeRes getMaintenanceTypes() {
        return new MaintenanceTypeRes();
    }
}
