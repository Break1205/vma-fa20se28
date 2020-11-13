package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.ContractStatus;

public class Contract {
    private String contractId;
    private ContractStatus contractStatus;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractId='" + contractId + '\'' +
                ", contractStatus=" + contractStatus +
                '}';
    }
}
