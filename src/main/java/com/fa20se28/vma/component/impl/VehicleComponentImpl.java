package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.mapper.VehicleMapper;
import com.fa20se28.vma.model.Vehicle;
import com.fa20se28.vma.model.VehicleStatus;
import com.fa20se28.vma.model.VehicleType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleComponentImpl implements VehicleComponent {
    private final VehicleMapper vehicleMapper;

    public VehicleComponentImpl(VehicleMapper vehicleMapper) {
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public int getTotal(int viewOption, String ownerId) {
        return vehicleMapper.getTotal(viewOption, ownerId);
    }

    @Override
    public List<VehicleType> getTypes() {
        return vehicleMapper.getTypes();
    }

    @Override
    public List<VehicleStatus> getStatus() {
        return vehicleMapper.getStatus();
    }

    @Override
    public List<Vehicle> getVehicles(String vehicleId, String model, String vehicleType, float vehicleDisMin, float vehicleDisMax , String vehicleStatus, int viewOption, int pageNum, String ownerId) {
        return vehicleMapper.getVehicles(vehicleId, model, vehicleType, vehicleDisMin, vehicleDisMax, vehicleStatus, viewOption, pageNum*15, ownerId);
    }
}
