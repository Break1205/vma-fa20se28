package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.request.VehicleReq;
import com.fa20se28.vma.request.VehicleUpdateReq;
import com.fa20se28.vma.response.*;
import com.fa20se28.vma.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/count")
    public int getTotal(
            @RequestParam(required = false, defaultValue = "0") int viewOption,
            @RequestParam(required = false) String ownerId) {
        return vehicleService.getTotal(viewOption, ownerId);
    }

    @GetMapping
    public VehiclePageRes getVehicles(
            @RequestParam(required = false, defaultValue = "0") int viewOption,
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false) String ownerId,
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) String model,
            @RequestParam(required = false, defaultValue = "0") int vehicleTypeId,
            @RequestParam(required = false, defaultValue = "0") int seatsMin,
            @RequestParam(required = false, defaultValue = "0") int seatsMax,
            @RequestParam(required = false) VehicleStatus vehicleStatus,
            @RequestParam(required = false, defaultValue = "0") float vehicleMinDis,
            @RequestParam(required = false, defaultValue = "0") float vehicleMaxDis) {
        return vehicleService.getVehicles(
                new VehiclePageReq(vehicleId, model, vehicleTypeId, seatsMin, seatsMax, vehicleStatus, vehicleMinDis, vehicleMaxDis),
                viewOption, pageNum, ownerId);
    }

    @GetMapping("/dropdown")
    public VehicleDropDownRes getVehiclesDropDown(
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false) String ownerId,
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) String model,
            @RequestParam(required = false, defaultValue = "0") int vehicleTypeId,
            @RequestParam(required = false, defaultValue = "0") int seatsMin,
            @RequestParam(required = false, defaultValue = "0") int seatsMax) {
        return vehicleService.getVehiclesDropDown(
                new VehicleDropDownReq(vehicleId, model, vehicleTypeId, seatsMin, seatsMax), pageNum, ownerId);
    }

    @PatchMapping("/assignment/{vehicle-id}/{driver-id}")
    public void assignDriverWithVehicle(
            @PathVariable("vehicle-id") String vehicleId,
            @PathVariable("driver-id") String driverId) {
        vehicleService.assignDriverWithVehicle(vehicleId, driverId);
    }

    @PatchMapping("/assignment/{vehicle-id}")
    public void withdrawIssuedVehicle(@PathVariable("vehicle-id") String vehicleId) {
        vehicleService.withdrawIssuedVehicle(vehicleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVehicle(@RequestBody VehicleReq vehicle) {
        vehicleService.createVehicle(vehicle);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(String vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
    }

    @GetMapping("/{vehicle-id}")
    public VehicleDetailRes getVehicleById(@PathVariable("vehicle-id") String vehicleId) {
        return vehicleService.getVehicleDetails(vehicleId);
    }

    @PatchMapping
    public void updateVehicleById(@RequestBody VehicleUpdateReq vehicleUpdateReq) {
        vehicleService.updateVehicleDetails(vehicleUpdateReq);
    }

    @PatchMapping("/{vehicle-id}/status")
    public void updateVehicleStatusById(
            @PathVariable("vehicle-id") String vehicleId,
            @RequestBody VehicleStatus vehicleStatus) {
        vehicleService.updateVehicleStatus(vehicleId, vehicleStatus);
    }
}
