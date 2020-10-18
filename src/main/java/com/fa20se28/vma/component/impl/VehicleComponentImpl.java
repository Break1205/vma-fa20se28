package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.mapper.IssuedVehicleMapper;
import com.fa20se28.vma.mapper.VehicleMapper;
import com.fa20se28.vma.mapper.VehicleStatusMapper;
import com.fa20se28.vma.mapper.VehicleTypeMapper;
import com.fa20se28.vma.model.Vehicle;
import com.fa20se28.vma.model.VehicleStatus;
import com.fa20se28.vma.model.VehicleType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<Vehicle> getVehicles(String vehicleId, String model, String vehicleType, float vehicleDisMin, float vehicleDisMax, String vehicleStatus, int viewOption, int pageNum, String ownerId) {
        return vehicleMapper.getVehicles(vehicleId, model, vehicleType, vehicleDisMin, vehicleDisMax, vehicleStatus, viewOption, pageNum*15, ownerId);
    }

    @Override
    public List<Vehicle> getAvailableVehicles(String vehicleId, String model, String vehicleType, int pageNum, String ownerId) {
//        return vehicleMapper.getAvailableVehicles(vehicleId, model, vehicleType, pageNum, ownerId);
        return null;
    }

    @Override
    @Transactional
    public void assignVehicle(String vehicleId, String driverId) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtf.format(LocalDateTime.now());

        int row = issuedVehicleMapper.assignVehicle(vehicleId, driverId, date);

        if (row == 0)
        {
            throw new DataException("Unknown error occured. Data not added!");
        }
    }

    @Override
    @Transactional
    public void updateIssuedVehicle(String vehicleId) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtf.format(LocalDateTime.now());

        int row = issuedVehicleMapper.updateIssuedVehicle(vehicleId, date);

        if (row == 0)
        {
            throw new DataException("Unknown error occured. Data not modified!");
        }
    }
}
