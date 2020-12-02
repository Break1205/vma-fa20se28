package com.fa20se28.vma.response;

import com.fa20se28.vma.model.MaintenanceDetail;

public class MaintenanceDetailRes {
    private MaintenanceDetail maintenanceDetail;

    public MaintenanceDetailRes(MaintenanceDetail maintenanceDetail) {
        this.maintenanceDetail = maintenanceDetail;
    }

    public MaintenanceDetail getMaintenanceDetail() {
        return maintenanceDetail;
    }

    public void setMaintenanceDetail(MaintenanceDetail maintenanceDetail) {
        this.maintenanceDetail = maintenanceDetail;
    }
}
