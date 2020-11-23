package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class VehicleRecommendationReq {
    private int vehicleTypeId;
    private float averageSeatCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date durationFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date durationTo;

    public VehicleRecommendationReq(int vehicleTypeId, float averageSeatCount, Date durationFrom, Date durationTo) {
        this.vehicleTypeId = vehicleTypeId;
        this.averageSeatCount = averageSeatCount;
        this.durationFrom = durationFrom;
        this.durationTo = durationTo;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public float getAverageSeatCount() {
        return averageSeatCount;
    }

    public void setAverageSeatCount(float averageSeatCount) {
        this.averageSeatCount = averageSeatCount;
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
}
