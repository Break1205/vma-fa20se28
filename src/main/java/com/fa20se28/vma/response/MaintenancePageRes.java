package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Maintenance;

import java.util.List;

public class MaintenancePageRes {
    private List<Maintenance> maintenanceList;

    public MaintenancePageRes(List<Maintenance> maintenanceList) {
        this.maintenanceList = maintenanceList;
    }

    public List<Maintenance> getMaintenanceList() {
        return maintenanceList;
    }

    public void setMaintenanceList(List<Maintenance> maintenanceList) {
        this.maintenanceList = maintenanceList;
    }
}
