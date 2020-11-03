package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.ContractStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ContractPageReq {
    private String contractOwnerId;
    private ContractStatus contractStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date durationFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date durationTo;
    private float totalPriceMin;
    private float totalPriceMax;

    public ContractPageReq(String contractOwnerId, ContractStatus contractStatus, Date durationFrom, Date durationTo, float totalPriceMin, float totalPriceMax) {
        this.contractOwnerId = contractOwnerId;
        this.contractStatus = contractStatus;
        this.durationFrom = durationFrom;
        this.durationTo = durationTo;
        this.totalPriceMin = totalPriceMin;
        this.totalPriceMax = totalPriceMax;
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

    public float getTotalPriceMin() {
        return totalPriceMin;
    }

    public void setTotalPriceMin(float totalPriceMin) {
        this.totalPriceMin = totalPriceMin;
    }

    public float getTotalPriceMax() {
        return totalPriceMax;
    }

    public void setTotalPriceMax(float totalPriceMax) {
        this.totalPriceMax = totalPriceMax;
    }
}
