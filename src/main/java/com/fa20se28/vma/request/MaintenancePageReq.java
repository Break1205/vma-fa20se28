package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.MaintenanceType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class MaintenancePageReq {
    private String vehicleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private MaintenanceType maintenanceType;
    private float costMin;
    private float costMax;

    public MaintenancePageReq(String vehicleId, LocalDate startDate, LocalDate endDate, MaintenanceType maintenanceType, float costMin, float costMax) {
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maintenanceType = maintenanceType;
        this.costMin = costMin;
        this.costMax = costMax;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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

    public float getCostMin() {
        return costMin;
    }

    public void setCostMin(float costMin) {
        this.costMin = costMin;
    }

    public float getCostMax() {
        return costMax;
    }

    public void setCostMax(float costMax) {
        this.costMax = costMax;
    }
}
