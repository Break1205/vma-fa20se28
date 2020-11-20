package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.MaintenanceType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MaintenanceReq {
    private String vehicleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    private MaintenanceType maintenanceType;
    private float cost;
    private String imageLink;
    private String description;

    public MaintenanceReq(String vehicleId, Date startDate, Date endDate, MaintenanceType maintenanceType, float cost, String imageLink, String description) {
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maintenanceType = maintenanceType;
        this.cost = cost;
        this.imageLink = imageLink;
        this.description = description;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
