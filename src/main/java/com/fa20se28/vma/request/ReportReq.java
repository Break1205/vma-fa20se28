package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.Quarter;
import com.fa20se28.vma.enums.ReportType;

public class ReportReq {
    private String vehicleId;
    private Integer year;
    private Quarter quarter;
    private ReportType reportType;

    public ReportReq(String vehicleId, Integer year, Quarter quarter, ReportType reportType) {
        this.vehicleId = vehicleId;
        this.year = year;
        this.quarter = quarter;
        this.reportType = reportType;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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
