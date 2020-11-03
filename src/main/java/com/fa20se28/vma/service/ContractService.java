package com.fa20se28.vma.service;

import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import com.fa20se28.vma.request.ContractUpdateReq;
import com.fa20se28.vma.response.ContractPageRes;

public interface ContractService {
    void createContract(ContractReq contractReq);

    ContractPageRes getContracts(ContractPageReq contractPageReq, int viewOption, int pageNum);

    void updateContractStatus(ContractStatus contractStatus, int contractId);

    void updateContract(ContractUpdateReq contractUpdateReq);
}
