package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.ContractStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ContractLM {
    private String contractId;
    private String contractOwnerId;
    private ContractStatus contractStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date durationFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date durationTo;
    private float totalPrice;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractOwnerId() {
        return contractOwnerId;
    }

    public void setContractOwnerId(String contractOwnerId) {
        this.contractOwnerId = contractOwnerId;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Date getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(Date durationFrom) {
        this.durationFrom = durationFrom;
    }

    public Date getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(Date durationTo) {
        this.durationTo = durationTo;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
