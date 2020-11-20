package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.MaintenanceComponent;
import com.fa20se28.vma.request.MaintenancePageReq;
import com.fa20se28.vma.request.MaintenanceReq;
import com.fa20se28.vma.request.MaintenanceUpdateReq;
import com.fa20se28.vma.response.MaintenanceDetailRes;
import com.fa20se28.vma.response.MaintenancePageRes;
import com.fa20se28.vma.service.MaintenanceService;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    private final MaintenanceComponent maintenanceComponent;

    public MaintenanceServiceImpl(MaintenanceComponent maintenanceComponent) {
        this.maintenanceComponent = maintenanceComponent;
    }

    @Override
    public void createMaintenance(MaintenanceReq maintenanceReq) {
        maintenanceComponent.createMaintenance(maintenanceReq);
    }

    @Override
    public void updateMaintenance(MaintenanceUpdateReq maintenanceUpdateReq) {
        maintenanceComponent.updateMaintenance(maintenanceUpdateReq);
    }

    @Override
    public void deleteMaintenance(int maintenanceId) {
        maintenanceComponent.deleteMaintenance(maintenanceId);
    }

    @Override
    public int getTotalMaintenance(MaintenancePageReq maintenancePageReq, int viewOption) {
        return maintenanceComponent.getTotalMaintenance(maintenancePageReq, viewOption);
    }

    @Override
    public MaintenancePageRes getMaintenances(MaintenancePageReq maintenancePageReq, int viewOption, int pageNum) {
        return new MaintenancePageRes(maintenanceComponent.getMaintenances(maintenancePageReq, viewOption, pageNum));
    }

    @Override
    public MaintenanceDetailRes getMaintenanceDetail(int maintenanceId) {
        return new MaintenanceDetailRes(maintenanceComponent.getMaintenanceDetail(maintenanceId));
    }
}
