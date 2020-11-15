package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.Quarter;
import com.fa20se28.vma.enums.ReportType;

public class ReportReq {
    private Integer year;
    private Quarter quarter;
    private ReportType reportType;

    public ReportReq(Integer year, Quarter quarter, ReportType reportType) {
        this.year = year;
        this.quarter = quarter;
        this.reportType = reportType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
        this.quarter = quarter;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }
}
