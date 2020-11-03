package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import com.fa20se28.vma.request.ContractUpdateReq;
import com.fa20se28.vma.response.ContractPageRes;
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
    void createContract(@RequestBody ContractReq contractReq) {
        contractService.createContract(contractReq);
    }

    @GetMapping
    public ContractPageRes getContracts(
            @RequestParam(required = false) String contractOwnerId,
            @RequestParam(required = false) ContractStatus contractStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date durationFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date durationTo,
            @RequestParam(required = false, defaultValue = "0") float totalPriceMin,
            @RequestParam(required = false, defaultValue = "0") float totalPriceMax,
            @RequestParam(required = false, defaultValue = "0") int viewOption,
            @RequestParam(required = false, defaultValue = "0") int pageNum) {
        return contractService.getContracts(
                new ContractPageReq(contractOwnerId, contractStatus, durationFrom, durationTo, totalPriceMin, totalPriceMax),
                viewOption, pageNum);
    }

    @PatchMapping("/{contract-id}/status")
    void updateContractStatus(
            @PathVariable("contract-id") int contractId,
            @RequestParam ContractStatus contractStatus) {
        contractService.updateContractStatus(contractStatus, contractId);
    }

    @PatchMapping
    void updateContract(@RequestBody ContractUpdateReq contractUpdateReq) {
        contractService.updateContract(contractUpdateReq);
    }
}
