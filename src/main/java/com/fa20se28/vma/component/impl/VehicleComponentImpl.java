package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.mapper.IssuedVehicleMapper;
import com.fa20se28.vma.mapper.VehicleMapper;
import com.fa20se28.vma.mapper.VehicleStatusMapper;
import com.fa20se28.vma.mapper.VehicleTypeMapper;
import com.fa20se28.vma.model.Vehicle;
import com.fa20se28.vma.model.VehicleDropDown;
import com.fa20se28.vma.model.VehicleStatus;
import com.fa20se28.vma.model.VehicleType;
import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class VehicleComponentImpl implements VehicleComponent {
    private final VehicleMapper vehicleMapper;
    private final VehicleTypeMapper vehicleTypeMapper;
    private final VehicleStatusMapper vehicleStatusMapper;
    private final IssuedVehicleMapper issuedVehicleMapper;

    public VehicleComponentImpl(VehicleMapper vehicleMapper, VehicleTypeMapper vehicleTypeMapper, VehicleStatusMapper vehicleStatusMapper, IssuedVehicleMapper issuedVehicleMapper) {
        this.vehicleMapper = vehicleMapper;
        this.vehicleTypeMapper = vehicleTypeMapper;
        this.vehicleStatusMapper = vehicleStatusMapper;
        this.issuedVehicleMapper = issuedVehicleMapper;
    }

    @Override
    public int getTotal(int viewOption, String ownerId) {
        return vehicleMapper.getTotal(viewOption, ownerId);
    }

    @Override
    public List<VehicleType> getTypes() {
        return vehicleTypeMapper.getTypes();
    }

    @Override
    public List<VehicleStatus> getStatus() {
        return vehicleStatusMapper.getStatus();
    }

    @Override
    public List<Vehicle> getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId) {
        return vehicleMapper.getVehicles(request, viewOption, pageNum*15, ownerId);
    }

    @Override
    public List<VehicleDropDown> getAvailableVehicles(VehicleDropDownReq request, int pageNum, String ownerId) {
        return vehicleMapper.getAvailableVehicles(request, pageNum, ownerId);
    }

    @Override
    @Transactional
    public void assignVehicle(String vehicleId, String driverId) {
        int row = issuedVehicleMapper.assignVehicle(vehicleId, driverId);

        if (row == 0)
        {
            throw new DataException("Unknown error occured. Data not added!");
        }
    }

    @Override
    @Transactional
    public void withdrawVehicle(String vehicleId) {
        int row = issuedVehicleMapper.withdrawVehicle(vehicleId);

        if (row == 0)
        {
            throw new DataException("Unknown error occured. Data not modified!");
        }
    }
}
