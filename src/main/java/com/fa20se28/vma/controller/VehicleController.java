package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.response.VehiclePageRes;
import com.fa20se28.vma.response.VehicleStatusRes;
import com.fa20se28.vma.response.VehicleTypesRes;
import com.fa20se28.vma.service.VehicleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VehicleController {
    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles/total")
    public int getTotal(@RequestParam(required = false, defaultValue = "0") int viewOption,
                        @RequestParam(required = false) String ownerId){
        return vehicleService.getTotal(viewOption, ownerId);
    }

    @GetMapping("vehicles")
    public VehiclePageRes getVehicles(@RequestParam(required = false, defaultValue = "0") int viewOption,
                                      @RequestParam(required = false, defaultValue = "0") int pageNum,
                                      @RequestParam(required = false) String ownerId,
                                      VehiclePageReq request) {
        return vehicleService.getVehicles(request.getVehicleId(), request.getModel(), request.getVehicleType(), request.getVehicleMinDis(), request.getVehicleMaxDis(), request.getVehicleStatus(), viewOption, pageNum, ownerId);
    }

    @GetMapping("vehicles/types")
    public VehicleTypesRes getTypes(){
        return vehicleService.getTypes();
    }

    @GetMapping("vehicles/status")
    public VehicleStatusRes getStatus()
    {
        return  vehicleService.getStatus();
    }

    //get vehicle detail
    //create update delete
}
