package com.fa20se28.vma.service;

import com.fa20se28.vma.request.ContractPageReq;
import com.fa20se28.vma.request.ContractReq;
import com.fa20se28.vma.response.ContractPageRes;

public interface ContractService {
    void createContract(ContractReq contractReq);

    ContractPageRes getContracts(ContractPageReq contractPageReq, int viewOption, int pageNum);
}
