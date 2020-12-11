package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.ContractComponent;
import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractDetailRes;
import com.fa20se28.vma.response.ContractPageRes;
import com.fa20se28.vma.service.ContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractComponent contractComponent;

    public ContractServiceImpl(ContractComponent contractComponent) {
        this.contractComponent = contractComponent;
    }

    @Override
    @Transactional
    public void createContract(ContractReq contractReq) {
        contractComponent.createContract(contractReq);
    }

    @Override
    public ContractPageRes getContracts(ContractPageReq contractPageReq, int viewOption, int pageNum) {
        return new ContractPageRes(contractComponent.getContracts(contractPageReq, viewOption, pageNum));
    }

    @Override
    public int getTotalContracts(ContractPageReq contractPageReq, int viewOption) {
        return contractComponent.getTotalContracts(contractPageReq, viewOption);
    }

    @Override
    public void updateContractStatus(ContractStatus contractStatus, int contractId) {
        contractComponent.updateContractStatus(contractStatus, contractId);
    }

    @Override
    public void updateContract(ContractUpdateReq contractUpdateReq) {
        contractComponent.updateContract(contractUpdateReq);
    }

    @Override
    public void updateContractTrip(ContractTripUpdateReq contractTripUpdateReq) {
        contractComponent.updateContractTrip(contractTripUpdateReq);
    }

    @Override
    public void updateContractTripVehicles(int contractTripId, List<String> assignedVehicles) {
        contractComponent.updateContractTripVehicles(contractTripId, assignedVehicles);
    }

    @Override
    public void updateContractTripSchedule(ContractTripScheduleUpdateReq contractTripScheduleUpdateReq) {
        contractComponent.updateContractTripSchedule(contractTripScheduleUpdateReq);
    }

    @Override
    public ContractDetailRes getContractDetails(int contractId) {
        return new ContractDetailRes(contractComponent.getContractDetails(contractId));
    }
}
