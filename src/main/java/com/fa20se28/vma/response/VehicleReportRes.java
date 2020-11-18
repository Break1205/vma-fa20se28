package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleReport;

import java.util.List;

public class VehicleReportRes {
    private List<VehicleReport> vehicleReports;

    public List<VehicleReport> getVehicleReports() {
        return vehicleReports;
    }

    public void setVehicleReports(List<VehicleReport> vehicleReports) {
        this.vehicleReports = vehicleReports;
    }
}
