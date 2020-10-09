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
    public int getTotal(int viewOption, String ownerId) {
        return vehicleComponent.getTotal(viewOption, ownerId);
    }

    @Override
    public VehiclePageRes getVehicles(String vehicleId, String model, String vehicleType, Float vehicleDisMin, Float vehicleDisMax , String vehicleStatus, int viewOption, int pageNum, String ownerId) {
        float minDis = -1, maxDis = -1;
        if (vehicleDisMin != null)
        {
            minDis = vehicleDisMin;
        }

        if (vehicleDisMax !=null)
        {
            maxDis = vehicleDisMax;
        }
        return new VehiclePageRes((pageNum + 1) + "/" + ((int) Math.ceil(20/15.0)), vehicleComponent.getVehicles(vehicleId, model, vehicleType, minDis, maxDis, vehicleStatus, viewOption, pageNum, ownerId));
    }
}
