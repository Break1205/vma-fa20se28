package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.model.ContractDetail;
import com.fa20se28.vma.model.ContractLM;
import com.fa20se28.vma.request.*;

import java.util.List;

public interface ContractComponent {
    void createContract(ContractReq contractReq);

    List<ContractLM> getContracts(ContractPageReq contractPageReq, int viewOption, int pageNum);

    int getTotalContracts(ContractPageReq contractPageReq, int viewOption);

    void updateContractStatus(ContractStatus contractStatus, int contractId);

    void updateContract(ContractUpdateReq contractUpdateReq);

    void updateContractTrip(ContractTripUpdateReq contractTripUpdateReq);

    void updateContractTripSchedule(ContractTripScheduleUpdateReq contractTripScheduleUpdateReq);

    ContractDetail getContractDetails(int contractId);
}
