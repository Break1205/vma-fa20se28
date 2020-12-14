package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import com.fa20se28.vma.response.ContractDetailRes;
import com.fa20se28.vma.response.ContractPageRes;
import com.fa20se28.vma.response.ContractStatusRes;
import com.fa20se28.vma.service.ContractService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate durationFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate durationTo,
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
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate durationFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate durationTo,
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

    @PutMapping
    public void updateContract(@RequestBody ContractReq contractReq) {
        contractService.updateContract(contractReq);
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
