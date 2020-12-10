package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.ContractStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ContractPageReq {
    private ContractStatus contractStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate durationFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate durationTo;
    private float totalPriceMin;
    private float totalPriceMax;

    public ContractPageReq(ContractStatus contractStatus, LocalDate durationFrom, LocalDate durationTo, float totalPriceMin, float totalPriceMax) {
        this.contractStatus = contractStatus;
        this.durationFrom = durationFrom;
        this.durationTo = durationTo;
        this.totalPriceMin = totalPriceMin;
        this.totalPriceMax = totalPriceMax;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public LocalDate getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(LocalDate durationFrom) {
        this.durationFrom = durationFrom;
    }

    public LocalDate getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(LocalDate durationTo) {
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
