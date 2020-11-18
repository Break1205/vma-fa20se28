package com.fa20se28.vma.response;

import com.fa20se28.vma.model.MaintenanceReport;

import java.util.List;

public class MaintenanceReportRes {
    private List<MaintenanceReport> maintenanceReports;

    public List<MaintenanceReport> getMaintenanceReports() {
        return maintenanceReports;
    }

    public void setMaintenanceReports(List<MaintenanceReport> maintenanceReports) {
        this.maintenanceReports = maintenanceReports;
    }
}
