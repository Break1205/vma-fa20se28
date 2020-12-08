package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.request.*;
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
    public int getTotal(VehiclePageReq request, int viewOption, String ownerId) {
        return vehicleComponent.getTotal(request, viewOption, ownerId);
    }

    @Override
    public VehiclePageRes getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId, int takeAll) {
        return new VehiclePageRes(vehicleComponent.getVehicles(request, viewOption, pageNum, ownerId, takeAll));
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
        vehicleComponent.createVehicle(vehicle, false);
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

    @Override
    public void updateVehicleStatus(String vehicleId, VehicleStatus vehicleStatus) {
        vehicleComponent.updateVehicleStatus(vehicleId, vehicleStatus);
    }

    @Override
    public DriverHistoryRes getDriverHistory(String vehicleId) {
        return new DriverHistoryRes(vehicleComponent.getDriverHistory(vehicleId));
    }

    @Override
    public VehicleCurrentRes getCurrentlyAssignedVehicle(String driverId) {
        return new VehicleCurrentRes(vehicleComponent.getCurrentlyAssignedVehicle(driverId));
    }

    @Override
    public VehicleHistoryRes getVehicleHistory(String driverId) {
        return new VehicleHistoryRes(vehicleComponent.getVehicleHistory(driverId));
    }

    @Override
    public void addVehicleValue(VehicleValueReq vehicleValueReq) {
        vehicleComponent.addVehicleValue(vehicleValueReq);
    }

    @Override
    public void updateVehicleValue(VehicleValueUpdateReq vehicleValueUpdateReq) {
        vehicleComponent.updateVehicleValue(vehicleValueUpdateReq);
    }

    @Override
    public void deleteVehicleValue(int vehicleValueId) {
        vehicleComponent.deleteVehicleValue(vehicleValueId);
    }

    @Override
    public VehicleOverviewRes getOverview(String ownerId) {
        return new VehicleOverviewRes(
                vehicleComponent.getTotal(
                        new VehiclePageReq(null, null, 0, 0, 0, VehicleStatus.AVAILABLE, 0, 0), 1, ownerId),
                vehicleComponent.getTotal(
                        new VehiclePageReq(null, null, 0, 0, 0, VehicleStatus.ON_ROUTE, 0, 0), 1, ownerId),
                vehicleComponent.getTypeCount(ownerId),
                vehicleComponent.getStatusCount(ownerId));
    }
}
