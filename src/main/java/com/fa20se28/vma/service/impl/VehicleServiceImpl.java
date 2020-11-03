package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.request.VehicleReq;
import com.fa20se28.vma.request.VehicleUpdateReq;
import com.fa20se28.vma.response.*;
import com.fa20se28.vma.service.VehicleService;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleComponent vehicleComponent;

    public VehicleServiceImpl(VehicleComponent vehicleComponent) {
        this.vehicleComponent = vehicleComponent;
    }

    @Override
    public int getTotal(int viewOption, String ownerId) {
        return vehicleComponent.getTotal(viewOption, ownerId);
    }

    @Override
    public VehiclePageRes getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId) {
        return new VehiclePageRes(vehicleComponent.getVehicles(request, viewOption, pageNum, ownerId));
    }

    @Override
    public VehicleDropDownRes getVehiclesDropDown(VehicleDropDownReq request, int pageNum, String ownerId) {
        return new VehicleDropDownRes(vehicleComponent.getVehiclesDropDown(request, pageNum, ownerId));
    }

    @Override
    public void assignDriverWithVehicle(String vehicleId, String driverId) {
        vehicleComponent.assignVehicle(vehicleId, driverId);
    }

    @Override
    public void withdrawIssuedVehicle(String vehicleId) {
        vehicleComponent.withdrawVehicle(vehicleId);
    }

    @Override
    public void createVehicle(VehicleReq vehicle) {
        vehicleComponent.createVehicle(vehicle);
    }

    @Override
    public void deleteVehicle(String vehicleId) {
        vehicleComponent.deleteVehicle(vehicleId);
    }

    @Override
    public VehicleDetailRes getVehicleDetails(String vehicleId) {
        return new VehicleDetailRes(vehicleComponent.getVehicleDetails(vehicleId));
    }

    @Override
    public void updateVehicleDetails(VehicleUpdateReq vehicleUpdateReq) {
        vehicleComponent.updateVehicleDetails(vehicleUpdateReq);
    }
}
