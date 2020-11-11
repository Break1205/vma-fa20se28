package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.model.ContractDetail;
import com.fa20se28.vma.model.ContractLM;
import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import com.fa20se28.vma.request.ContractUpdateReq;

import java.util.List;

public interface ContractComponent {
    void createContract(ContractReq contractReq);

    List<ContractLM> getContracts(ContractPageReq contractPageReq, int viewOption, int pageNum);

    void updateContractStatus(ContractStatus contractStatus, int contractId);

    void updateContract(ContractUpdateReq contractUpdateReq);

    ContractDetail getContractDetails(int contractId);
}
