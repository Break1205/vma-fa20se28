package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.BrandRes;
import com.fa20se28.vma.response.SeatsRes;
import com.fa20se28.vma.response.VehicleTypesRes;
import com.fa20se28.vma.service.VehicleMiscService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles/misc")
public class VehicleMiscController {
    private final VehicleMiscService vehicleMiscService;

    public VehicleMiscController(VehicleMiscService vehicleMiscService) {
        this.vehicleMiscService = vehicleMiscService;
    }

    @GetMapping("/types")
    public VehicleTypesRes getTypes(){
        return vehicleMiscService.getTypes();
    }

    @GetMapping("/brands")
    public BrandRes getBrands() {
        return vehicleMiscService.getBrands();
    }

    @GetMapping("/seats")
    public SeatsRes getSeats() {
        return vehicleMiscService.getSeats();
    }

    @PostMapping("/brands")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBrand(@RequestBody BrandReq brandReq) {
        vehicleMiscService.createBrand(brandReq);
    }

    @PatchMapping("/brands")
    public void updateBrand(@RequestBody BrandUpdateReq brandUpdateReq) {
        vehicleMiscService.updateBrand(brandUpdateReq);
    }

    @PostMapping("/types")
    @ResponseStatus(HttpStatus.CREATED)
    public void createType(@RequestBody VehicleTypeReq vehicleTypeReq) {
        vehicleMiscService.createType(vehicleTypeReq);
    }

    @PatchMapping("/types")
    public void updateType(@RequestBody VehicleTypeUpdateReq vehicleTypeUpdateReq) {
        vehicleMiscService.updateType(vehicleTypeUpdateReq);
    }

    @PostMapping("/seats")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSeats(@RequestBody SeatsReq seatsReq) {
        vehicleMiscService.createSeats(seatsReq);
    }

    @PatchMapping("/seats")
    public void updateSeats(@RequestBody SeatsReq seatsReq) {
        vehicleMiscService.updateSeats(seatsReq);
    }
}
