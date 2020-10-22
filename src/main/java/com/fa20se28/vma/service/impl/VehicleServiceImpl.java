package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.response.VehicleDropDownRes;
import com.fa20se28.vma.response.VehiclePageRes;
import com.fa20se28.vma.response.VehicleStatusRes;
import com.fa20se28.vma.response.VehicleTypesRes;
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
    public VehicleTypesRes getTypes() {
        return new VehicleTypesRes(vehicleComponent.getTypes());
    }

    @Override
    public VehicleStatusRes getStatus() {
        return new VehicleStatusRes(vehicleComponent.getStatus());
    }


    @Override
    public VehiclePageRes getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId) {
        return new VehiclePageRes(vehicleComponent.getVehicles(request, viewOption, pageNum, ownerId));
    }

    @Override
    public VehicleDropDownRes getAvailableVehicles(VehicleDropDownReq request, int pageNum, String ownerId) {
        return new VehicleDropDownRes(vehicleComponent.getAvailableVehicles(request, pageNum, ownerId));
    }

    @Override
    public void assignDriverWithVehicle(String vehicleId, String driverId) {
        vehicleComponent.assignVehicle(vehicleId, driverId);
    }

    @Override
    public void withdrawIssuedVehicle(String vehicleId) {
        vehicleComponent.withdrawVehicle(vehicleId);
    }
}
