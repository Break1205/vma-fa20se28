package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.request.VehicleReq;
import com.fa20se28.vma.response.VehicleDetailRes;
import com.fa20se28.vma.response.VehicleDropDownRes;
import com.fa20se28.vma.response.VehiclePageRes;
import com.fa20se28.vma.response.VehicleTypesRes;
import com.fa20se28.vma.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {
    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("count")
    public int getTotal(
            @RequestParam(required = false, defaultValue = "0") int viewOption,
            @RequestParam(required = false) String ownerId){
        return vehicleService.getTotal(viewOption, ownerId);
    }

    @GetMapping
    public VehiclePageRes getVehicles(
            @RequestParam(required = false, defaultValue = "0") int viewOption,
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false) String ownerId,
            VehiclePageReq request) {
        return vehicleService.getVehicles(request, viewOption, pageNum, ownerId);
    }

    @GetMapping("/dropdown")
    public VehicleDropDownRes getVehiclesDropDown(
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false) String ownerId,
            VehicleDropDownReq request,
            String status) {
        return vehicleService.getVehiclesDropDown(request, pageNum, status, ownerId);
    }

    @GetMapping("types")
    public VehicleTypesRes getTypes(){
        return vehicleService.getTypes();
    }

    @PostMapping("assignment/{vehicle_id}/{driver_id}")
    public void assignDriverWithVehicle(
            @PathVariable("vehicle_id") String vehicleId,
            @PathVariable("driver_id") String driverId) {
        vehicleService.assignDriverWithVehicle(vehicleId, driverId);
    }

    @PutMapping("assignment/{vehicle_id}")
    public void withdrawIssuedVehicle(@PathVariable("vehicle_id") String vehicleId) {
        vehicleService.withdrawIssuedVehicle(vehicleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVehicle(@RequestBody VehicleReq vehicle) {
        vehicleService.createVehicle(vehicle);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(String vehicleId)
    {
        vehicleService.deleteVehicle(vehicleId);
    }

    @GetMapping("/{vehicle_id}")
    public VehicleDetailRes getVehicleById(@PathVariable("vehicle_id") String vehicleId) {
        return vehicleService.getVehicleDetails(vehicleId);
    }
}
