package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.*;
import com.fa20se28.vma.service.ContractVehicleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    public void assignVehicleForContractTrip(@RequestBody ContractVehicleReq contractVehicleReq) {
        contractVehicleService.assignVehicleForContract(contractVehicleReq);
    }

    @GetMapping
    public ContractVehicleRes getContractVehiclesByContractTripId(@RequestParam int contractTripId) {
        return contractVehicleService.getContractVehiclesByContractTripId(contractTripId);
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

    @GetMapping("/{issued-vehicle-id}/trips/{contract-detail-id}")
    public ContractVehicleStatus getVehicleStatusByVehicleIdAndContractTripId(
            @PathVariable("contract-detail-id") int contractTripId,
            @PathVariable("issued-vehicle-id") int issuedVehicleId) {
        return contractVehicleService.getVehicleStatus(contractTripId, issuedVehicleId);
    }

    @GetMapping("/{issued-vehicle-id}/trips")
    public TripListRes getTrips(
            @PathVariable("issued-vehicle-id") int issuedVehicleId,
            @RequestParam(required = false) ContractVehicleStatus vehicleStatus,
            @RequestParam(required = false, defaultValue = "0") int page) {
        return contractVehicleService.getTrips(new TripListReq(issuedVehicleId, vehicleStatus), page);
    }

    @GetMapping("/{issued-vehicle-id}/trips/current")
    public TripListRes getCurrentTrip(@PathVariable("issued-vehicle-id") int issuedVehicleId) {
        return contractVehicleService.getCurrentTrip(issuedVehicleId);
    }

    @GetMapping("/available-vehicles/auto")
    public VehicleContractRes getAvailableVehiclesAuto(
            @RequestParam(required = false, defaultValue = "0") int vehicleCount,
            @RequestParam(required = false, defaultValue = "0") int passengerCount,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            @RequestParam(required = false, defaultValue = "0") int displayAll,
            @RequestParam(required = false, defaultValue = "0") int pageNum) {
        return contractVehicleService.getAvailableVehiclesAuto(vehicleCount, passengerCount, startDate, endDate, displayAll, pageNum);
    }

    @GetMapping("/available-vehicles/count/auto")
    public int getTotalAvailableVehiclesAuto(
            @RequestParam(required = false, defaultValue = "0") int vehicleCount,
            @RequestParam(required = false, defaultValue = "0") int passengerCount,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            @RequestParam(required = false, defaultValue = "0") int displayAll) {
        return contractVehicleService.getTotalAvailableVehiclesAuto(vehicleCount, passengerCount, startDate, endDate, displayAll);
    }


    @GetMapping("/available-vehicles")
    public VehicleContractRes getAvailableVehicles(
            @RequestParam(required = false, defaultValue = "0") int vehicleTypeId,
            @RequestParam(required = false, defaultValue = "0") int seatsMin,
            @RequestParam(required = false, defaultValue = "0") int seatsMax,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            @RequestParam(required = false) String yearMin,
            @RequestParam(required = false) String yearMax,
            @RequestParam(required = false, defaultValue = "0") int displayAll,
            @RequestParam(required = false, defaultValue = "0") int pageNum) {
        return contractVehicleService.getAvailableVehicles(new VehicleContractReq(vehicleTypeId, seatsMin, seatsMax, startDate, endDate, yearMin, yearMax), pageNum, displayAll);
    }

    @GetMapping("/available-vehicles/count")
    public int getTotalAvailableVehicles(
            @RequestParam(required = false, defaultValue = "0") int vehicleTypeId,
            @RequestParam(required = false, defaultValue = "0") int seatsMin,
            @RequestParam(required = false, defaultValue = "0") int seatsMax,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            @RequestParam(required = false) String yearMin,
            @RequestParam(required = false) String yearMax,
            @RequestParam(required = false, defaultValue = "0") int displayAll) {
        return contractVehicleService.getTotalAvailableVehicles(new VehicleContractReq(vehicleTypeId, seatsMin, seatsMax, startDate, endDate, yearMin, yearMax), displayAll);
    }
}
