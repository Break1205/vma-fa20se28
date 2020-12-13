package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.Quarter;
import com.fa20se28.vma.enums.ReportType;

public class ReportReq {
    private String vehicleId;
    private Integer year;
    private Quarter quarter;
    private ReportType reportType;
    private String userId;
    private String status;

    public ReportReq(String userId, String vehicleId, Integer year, Quarter quarter, ReportType reportType, String status) {
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.year = year;
        this.quarter = quarter;
        this.reportType = reportType;
        this.status = status;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
