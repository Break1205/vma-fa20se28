package com.fa20se28.vma.service;

import com.fa20se28.vma.request.MaintenancePageReq;
import com.fa20se28.vma.request.MaintenanceReq;
import com.fa20se28.vma.request.MaintenanceUpdateReq;
import com.fa20se28.vma.response.MaintenanceDetailRes;
import com.fa20se28.vma.response.MaintenancePageRes;

public interface MaintenanceService {
    void createMaintenance(MaintenanceReq maintenanceReq);

    void updateMaintenance(MaintenanceUpdateReq maintenanceUpdateReq);

    void deleteMaintenance(int maintenanceId);

    int getTotalMaintenance(MaintenancePageReq maintenancePageReq, int viewOption);

    MaintenancePageRes getMaintenances(MaintenancePageReq maintenancePageReq, int viewOption, int pageNum);

    MaintenanceDetailRes getMaintenanceDetail(int maintenanceId);
}
