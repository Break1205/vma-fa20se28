package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.ContractVehiclePassengerReq;
import com.fa20se28.vma.request.ContractVehicleReq;
import com.fa20se28.vma.response.ContractVehicleRes;
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

    @GetMapping("/passengers")
    public PassengerRes getPassengerList(@RequestParam int contractVehicleId) {
        return contractVehicleService.getPassengerList(contractVehicleId);
    }

    @PostMapping("/passengers")
    public void createPassengerList(@RequestBody ContractVehiclePassengerReq contractVehiclePassengerReq) {
        contractVehicleService.createPassengerList(contractVehiclePassengerReq);
    }

    @PostMapping
    public void assignVehicleForContract(@RequestBody ContractVehicleReq contractVehicleReq) {
        contractVehicleService.assignVehicleForContract(contractVehicleReq);
    }

    @GetMapping
    public ContractVehicleRes getContractVehiclesByContractId(@RequestParam int contractId) {
        return contractVehicleService.getContractVehicles(contractId);
    }
}
