package com.fa20se28.vma.model;

import com.fa20se28.vma.enums.MaintenanceType;

import java.time.LocalDate;

public class MaintenanceReport {
    private LocalDate maintenanceDate;
    private MaintenanceType maintenanceType;
    private float cost;

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
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
}
