package com.fa20se28.vma.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AssignedVehicle {
    private int issuedVehicleId;
    private String vehicleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date issuedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date returnedDate;

    public int getIssuedVehicleId() {
        return issuedVehicleId;
    }

    public void setIssuedVehicleId(int issuedVehicleId) {
        this.issuedVehicleId = issuedVehicleId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }
}
