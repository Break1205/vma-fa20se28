package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.response.VehiclePageRes;
import com.fa20se28.vma.service.VehicleService;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {
    private VehicleComponent vehicleComponent;

    public VehicleServiceImpl(VehicleComponent vehicleComponent) {
        this.vehicleComponent = vehicleComponent;
    }

    @Override
    public int getTotal() {
        return vehicleComponent.getTotal();
    }

    @Override
    public VehiclePageRes getVehicles() {
        return new VehiclePageRes(vehicleComponent.getTotal(), vehicleComponent.getVehicles());
    }
}
