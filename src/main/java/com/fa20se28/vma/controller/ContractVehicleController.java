package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractVehicleRes;
import com.fa20se28.vma.response.ContractVehicleStatusRes;
import com.fa20se28.vma.response.PassengerRes;
import com.fa20se28.vma.response.TripListRes;
import com.fa20se28.vma.service.ContractVehicleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PatchMapping
    public void updateContractVehicleStatus(@RequestBody ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq) {
        contractVehicleService.updateContractVehicleStatus(contractVehicleStatusUpdateReq);
    }

    @GetMapping("/status")
    public ContractVehicleStatusRes getContractVehicleStatus() {
        return new ContractVehicleStatusRes();
    }

    @PatchMapping("/start")
    public void startTrip(@RequestBody TripReq tripReq) {
        contractVehicleService.startTrip(tripReq);
    }

    @PatchMapping("/end")
    public void endTrip(@RequestBody TripReq tripReq) {
        contractVehicleService.endTrip(tripReq);
    }

    @GetMapping("/{issued-vehicle-id}/trips/{contract-id}")
    public ContractVehicleStatus getVehicleStatusByVehicleIdAndContractId(
            @PathVariable("contract-id") int contractId,
            @PathVariable("issued-vehicle-id") int issuedVehicleId) {
        return contractVehicleService.getVehicleStatus(contractId, issuedVehicleId);
    }

    @GetMapping("/{issued-vehicle-id}/trips")
    public TripListRes getTrips(
            @PathVariable("issued-vehicle-id") int issuedVehicleId,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date departureTime,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date destinationTime,
            @RequestParam(required = false) ContractVehicleStatus vehicleStatus) {
        return contractVehicleService.getTrips(new TripListReq(issuedVehicleId, departureTime, destinationTime, vehicleStatus));
    }
}
