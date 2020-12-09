package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.MaintenanceType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class MaintenanceUpdateReq {
    private int maintenanceId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private MaintenanceType maintenanceType;
    private float cost;
    private String imageLink;
    private String description;

    public MaintenanceUpdateReq(int maintenanceId, LocalDate startDate, LocalDate endDate, MaintenanceType maintenanceType, float cost, String imageLink, String description) {
        this.maintenanceId = maintenanceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maintenanceType = maintenanceType;
        this.cost = cost;
        this.imageLink = imageLink;
        this.description = description;
    }

    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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
