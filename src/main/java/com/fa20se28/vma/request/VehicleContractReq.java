package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class VehicleContractReq {
    private int vehicleTypeId;
    private int seatsMin;
    private int seatsMax;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    private String yearMin;
    private String yearMax;

    public VehicleContractReq(int vehicleTypeId, int seatsMin, int seatsMax, LocalDateTime startDate, LocalDateTime endDate, String yearMin, String yearMax) {
        this.vehicleTypeId = vehicleTypeId;
        this.seatsMin = seatsMin;
        this.seatsMax = seatsMax;
        this.startDate = startDate;
        this.endDate = endDate;
        this.yearMin = yearMin;
        this.yearMax = yearMax;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public int getSeatsMin() {
        return seatsMin;
    }

    public void setSeatsMin(int seatsMin) {
        this.seatsMin = seatsMin;
    }

    public int getSeatsMax() {
        return seatsMax;
    }

    public void setSeatsMax(int seatsMax) {
        this.seatsMax = seatsMax;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getYearMin() {
        return yearMin;
    }

    public void setYearMin(String yearMin) {
        this.yearMin = yearMin;
    }

    public String getYearMax() {
        return yearMax;
    }

    public void setYearMax(String yearMax) {
        this.yearMax = yearMax;
    }
}
