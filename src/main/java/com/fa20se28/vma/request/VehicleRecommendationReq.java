package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class VehicleRecommendationReq {
    private int vehicleTypeId;
    private int seatsMin;
    private int seatsMax;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    public VehicleRecommendationReq(int vehicleTypeId, int seatsMin, int seatsMax, Date startDate, Date endDate) {
        this.vehicleTypeId = vehicleTypeId;
        this.seatsMin = seatsMin;
        this.seatsMax = seatsMax;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
