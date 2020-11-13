package com.fa20se28.vma.response;

import com.fa20se28.vma.model.ContractLM;

import java.util.List;

public class ContractPageRes {
    private List<ContractLM> contractList;

    public ContractPageRes(List<ContractLM> contractList) {
        this.contractList = contractList;
    }

    public List<ContractLM> getContractList() {
        return contractList;
    }

    public void setContractList(List<ContractLM> contractList) {
        this.contractList = contractList;
    }
}
