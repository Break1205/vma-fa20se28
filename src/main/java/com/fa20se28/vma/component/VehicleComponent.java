package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.request.VehicleReq;
import com.fa20se28.vma.request.VehicleUpdateReq;

import java.util.List;

public interface VehicleComponent {
    int getTotal(VehiclePageReq request, int viewOption, String ownerId);

    List<Vehicle> getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId, int takeAll);

    List<VehicleDropDown> getVehiclesDropDown(VehicleDropDownReq request, int pageNum, String ownerId);

    void assignVehicle(String vehicleId, String driverId);

    void withdrawVehicle(String vehicleId);

    void createVehicle(VehicleReq vehicle, boolean notAdmin);

    void deleteVehicle(String vehicleId);

    VehicleDetail getVehicleDetails(String vehicleId);

    void updateVehicleDetails(VehicleUpdateReq vehicleUpdateReq);

    void updateVehicleStatus(String vehicleId, VehicleStatus vehicleStatus);

    List<DriverHistory> getDriverHistory(String vehicleId);

    AssignedVehicle getCurrentlyAssignedVehicle(String driverId);

    void createVehicleFromRequest(VehicleReq vehicle);

    void acceptVehicle(String vehicleId);

    void denyVehicle(String vehicleId, int requestId);
}
