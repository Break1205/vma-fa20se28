package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FeedbackPageReq {
    private String vehicleId;
    private String contributorId;
    private String contributorName;
    private String driverId;
    private String driverName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date toDate;
    private int rateMin;
    private int rateMax;

    public FeedbackPageReq(String vehicleId, String contributorId, String contributorName, String driverId, String driverName, Date fromDate, Date toDate, int rateMin, int rateMax) {
        this.vehicleId = vehicleId;
        this.contributorId = contributorId;
        this.contributorName = contributorName;
        this.driverId = driverId;
        this.driverName = driverName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.rateMin = rateMin;
        this.rateMax = rateMax;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getContributorId() {
        return contributorId;
    }

    public void setContributorId(String contributorId) {
        this.contributorId = contributorId;
    }

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getRateMin() {
        return rateMin;
    }

    public void setRateMin(int rateMin) {
        this.rateMin = rateMin;
    }

    public int getRateMax() {
        return rateMax;
    }

    public void setRateMax(int rateMax) {
        this.rateMax = rateMax;
    }
}
