package com.fa20se28.vma.response;

import com.fa20se28.vma.model.ContractReport;

import java.util.List;

public class ContractReportRes {
    private List<ContractReport> contractReports;

    public List<ContractReport> getContractReports() {
        return contractReports;
    }

    public void setContractReports(List<ContractReport> contractReports) {
        this.contractReports = contractReports;
    }
}
