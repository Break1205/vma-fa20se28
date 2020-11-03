package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.ContractVehiclePassengerReq;
import com.fa20se28.vma.response.PassengerRes;
import com.fa20se28.vma.service.ContractVehicleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contracts/vehicles")
public class ContractVehicleController {
    private final ContractVehicleService contractVehicleService;

    public ContractVehicleController(ContractVehicleService contractVehicleService) {
        this.contractVehicleService = contractVehicleService;
    }

    @GetMapping
    public PassengerRes getPassengerList(@RequestParam int contractVehicleId) {
        return contractVehicleService.getPassengerList(contractVehicleId);
    }

    @PostMapping
    public void createPassengerList(@RequestBody ContractVehiclePassengerReq contractVehiclePassengerReq)
    {
        contractVehicleService.createPassengerList(contractVehiclePassengerReq);
    }
}
