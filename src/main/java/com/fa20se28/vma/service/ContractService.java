package com.fa20se28.vma.service;

import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.ContractDetailRes;
import com.fa20se28.vma.response.ContractPageRes;

public interface ContractService {
    void createContract(ContractReq contractReq);

    ContractPageRes getContracts(ContractPageReq contractPageReq, int viewOption, int pageNum);

    int getTotalContracts(ContractPageReq contractPageReq, int viewOption);

    void updateContractStatus(ContractStatus contractStatus, int contractId);

    void updateContract(ContractUpdateReq contractUpdateReq);

    void updateContractTrip(ContractTripUpdateReq contractTripUpdateReq);

    void updateContractTripSchedule(ContractTripScheduleUpdateReq contractTripScheduleUpdateReq);

    ContractDetailRes getContractDetails(int contractId);
}
