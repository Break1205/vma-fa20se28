package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.MaintenanceComponent;
import com.fa20se28.vma.configuration.exception.DataExecutionException;
import com.fa20se28.vma.mapper.MaintenanceMapper;
import com.fa20se28.vma.model.Maintenance;
import com.fa20se28.vma.model.MaintenanceDetail;
import com.fa20se28.vma.request.MaintenancePageReq;
import com.fa20se28.vma.request.MaintenanceReq;
import com.fa20se28.vma.request.MaintenanceUpdateReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MaintenanceComponentImpl implements MaintenanceComponent {
    private final MaintenanceMapper maintenanceMapper;

    public MaintenanceComponentImpl(MaintenanceMapper maintenanceMapper) {
        this.maintenanceMapper = maintenanceMapper;
    }


    @Override
    @Transactional
    public void createMaintenance(MaintenanceReq maintenanceReq) {
        int row = maintenanceMapper.createMaintenance(maintenanceReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void updateMaintenance(MaintenanceUpdateReq maintenanceUpdateReq) {
        int row = maintenanceMapper.updateMaintenance(maintenanceUpdateReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void deleteMaintenance(int maintenanceId) {
        int row = maintenanceMapper.deleteMaintenance(maintenanceId);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    public int getTotalMaintenance(MaintenancePageReq maintenancePageReq, int viewOption) {
        return maintenanceMapper.getMaintenanceCount(maintenancePageReq, viewOption);
    }

    @Override
    public List<Maintenance> getMaintenances(MaintenancePageReq maintenancePageReq, int viewOption, int pageNum) {
        return maintenanceMapper.getMaintenances(maintenancePageReq, viewOption, pageNum*15);
    }

    @Override
    public MaintenanceDetail getMaintenanceDetail(int maintenanceId) {
        return maintenanceMapper.getMaintenanceDetail(maintenanceId);
    }


}
