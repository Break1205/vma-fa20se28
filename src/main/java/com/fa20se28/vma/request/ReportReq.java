package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.ReportType;

public class ReportReq {
    private String year;
    private String month;
    private ReportType reportType;



    public ReportReq(String year, String month, ReportType reportType) {
        this.year = year;
        this.month = month;
        this.reportType = reportType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }
}
