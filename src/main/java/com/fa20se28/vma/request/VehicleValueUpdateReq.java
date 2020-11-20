package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class VehicleValueUpdateReq {
    private int vehicleValueId;
    private float value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    public int getVehicleValueId() {
        return vehicleValueId;
    }

    public void setVehicleValueId(int vehicleValueId) {
        this.vehicleValueId = vehicleValueId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
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
