package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractDetailRes;
import com.fa20se28.vma.response.ContractPageRes;
import com.fa20se28.vma.response.ContractStatusRes;
import com.fa20se28.vma.service.ContractService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createContract(@RequestBody ContractReq contractReq) {
        contractService.createContract(contractReq);
    }

    @GetMapping
    public ContractPageRes getContracts(
            @RequestParam(required = false) ContractStatus contractStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date durationFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date durationTo,
            @RequestParam(required = false, defaultValue = "0") float totalPriceMin,
            @RequestParam(required = false, defaultValue = "0") float totalPriceMax,
            @RequestParam(required = false, defaultValue = "0") int viewOption,
            @RequestParam(required = false, defaultValue = "0") int pageNum) {
        return contractService.getContracts(
                new ContractPageReq(contractStatus, durationFrom, durationTo, totalPriceMin, totalPriceMax),
                viewOption, pageNum);
    }

    @GetMapping("/count")
    public int getTotalContracts(
            @RequestParam(required = false) ContractStatus contractStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date durationFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date durationTo,
            @RequestParam(required = false, defaultValue = "0") float totalPriceMin,
            @RequestParam(required = false, defaultValue = "0") float totalPriceMax,
            @RequestParam(required = false, defaultValue = "0") int viewOption) {
        return contractService.getTotalContracts(
                new ContractPageReq(contractStatus, durationFrom, durationTo, totalPriceMin, totalPriceMax),
                viewOption);
    }

    @PatchMapping("/{contract-id}/status")
    public void updateContractStatus(
            @PathVariable("contract-id") int contractId,
            @RequestParam ContractStatus contractStatus) {
        contractService.updateContractStatus(contractStatus, contractId);
    }

    @PatchMapping
    public void updateContract(@RequestBody ContractUpdateReq contractUpdateReq) {
        contractService.updateContract(contractUpdateReq);
    }

    @PatchMapping("/trip")
    public void updateContractTrip(@RequestBody ContractTripUpdateReq contractTripUpdateReq) {
        contractService.updateContractTrip(contractTripUpdateReq);
    }

    @PostMapping("/schedule")
    @ResponseStatus(HttpStatus.CREATED)
    public void createContractTripSchedule(@RequestBody ContractTripScheduleStandaloneReq contractTripScheduleStandaloneReq) {
        contractService.addContractTripSchedule(contractTripScheduleStandaloneReq);
    }

    @PatchMapping("/schedule")
    public void updateContractTripSchedule(@RequestBody ContractTripScheduleUpdateReq contractTripScheduleUpdateReq) {
        contractService.updateContractTripSchedule(contractTripScheduleUpdateReq);
    }

    @DeleteMapping("/schedule")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContractTripSchedule(@RequestParam int locationId) {
        contractService.deleteContractTripSchedule(locationId);
    }

    @GetMapping("/{contract-id}")
    public ContractDetailRes getContractById(@PathVariable("contract-id") int contractId) {
        return contractService.getContractDetails(contractId);
    }

    @GetMapping("/status")
    public ContractStatusRes getContractStatus() {
        return new ContractStatusRes();
    }
}
