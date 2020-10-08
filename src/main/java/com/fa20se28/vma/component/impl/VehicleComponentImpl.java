package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.mapper.VehicleMapper;
import com.fa20se28.vma.model.Vehicle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleComponentImpl implements VehicleComponent {
    private final VehicleMapper vehicleMapper;

    public VehicleComponentImpl(VehicleMapper vehicleMapper) {
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public int getTotal() {
        return vehicleMapper.getTotal();
    }

    @Override
    public List<Vehicle> getVehicles() {
        return vehicleMapper.getVehicles();
    }
}
