package com.fa20se28.vma.response;

import com.fa20se28.vma.model.ContractDetail;

public class ContractDetailRes {
    private ContractDetail contractDetail;

    public ContractDetailRes(ContractDetail contractDetail) {
        this.contractDetail = contractDetail;
    }

    public ContractDetail getContractDetail() {
        return contractDetail;
    }

    public void setContractDetail(ContractDetail contractDetail) {
        this.contractDetail = contractDetail;
    }
}
