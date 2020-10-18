package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.response.VehiclePageRes;
import com.fa20se28.vma.response.VehicleStatusRes;
import com.fa20se28.vma.response.VehicleTypesRes;
import com.fa20se28.vma.service.VehicleService;
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

    @GetMapping("")
    public VehiclePageRes getVehicles(
            @RequestParam(required = false, defaultValue = "0") int viewOption,
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false) String ownerId,
            VehiclePageReq request) {
        return vehicleService.getVehicles(request.getVehicleId(), request.getModel(), request.getVehicleType(), request.getVehicleMinDis(), request.getVehicleMaxDis(), request.getVehicleStatus(), viewOption, pageNum, ownerId);
    }

    @GetMapping("availability")
    public VehiclePageRes getAvailableVehicles(
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false) String ownerId,
          VehiclePageReq request) {
        return vehicleService.getAvailableVehicles(request.getVehicleId(), request.getModel(), request.getVehicleType(), pageNum, ownerId);
    }

    @GetMapping("types")
    public VehicleTypesRes getTypes(){
        return vehicleService.getTypes();
    }

    @GetMapping("status")
    public VehicleStatusRes getStatus()
    {
        return  vehicleService.getStatus();
    }

    //get vehicle detail
    //create update delete

    @PostMapping("assignment/{vehicle_id}/{driver_id}")
    public void assignDriverWithVehicle(
            @PathVariable("vehicle_id") String vehicleId,
            @PathVariable("driver_id") String driverId) {
        vehicleService.assignDriverWithVehicle(vehicleId, driverId);
    }

    @PutMapping("assignment/{vehicle_id}")
    public void updateIssuedVehicle(@PathVariable("vehicle_id") String vehicleId) {
        vehicleService.updateIssuedVehicle(vehicleId);
    }
}
