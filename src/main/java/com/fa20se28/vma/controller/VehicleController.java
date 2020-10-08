package com.fa20se28.vma.controller;

import com.fa20se28.vma.response.VehiclePageRes;
import com.fa20se28.vma.service.VehicleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VehicleController {
    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles/total")
    public int getTotal(){
        return vehicleService.getTotal();
    }

    @GetMapping("vehicles")
    public VehiclePageRes getVehicles() {
        return vehicleService.getVehicles();
    }
}
