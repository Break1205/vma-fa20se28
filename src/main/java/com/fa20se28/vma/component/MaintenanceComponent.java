package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Maintenance;
import com.fa20se28.vma.model.MaintenanceDetail;
import com.fa20se28.vma.request.MaintenancePageReq;
import com.fa20se28.vma.request.MaintenanceReq;
import com.fa20se28.vma.request.MaintenanceUpdateReq;

import java.util.List;

public interface MaintenanceComponent {
    void createMaintenance(MaintenanceReq maintenanceReq);

    void updateMaintenance(MaintenanceUpdateReq maintenanceUpdateReq);

    void deleteMaintenance(int maintenanceId);

    int getTotalMaintenance(MaintenancePageReq maintenancePageReq, int viewOption);

    List<Maintenance> getMaintenances(MaintenancePageReq maintenancePageReq, int viewOption, int pageNum);

    MaintenanceDetail getMaintenanceDetail(int maintenanceId);
}
