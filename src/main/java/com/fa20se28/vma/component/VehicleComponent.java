package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.*;

import java.util.List;

public interface VehicleComponent {
    int getTotal(VehiclePageReq request, int useStatus, String ownerId);

    List<Vehicle> getVehicles(VehiclePageReq request, int useStatus, int pageNum, String ownerId, int takeAll);

    void assignVehicle(String vehicleId, String driverId);

    void withdrawVehicle(String vehicleId);

    void createVehicle(VehicleReq vehicle, boolean notAdmin);

    void deleteVehicle(String vehicleId);

    VehicleDetail getVehicleDetails(String vehicleId);

    void updateVehicleDetails(VehicleUpdateReq vehicleUpdateReq);

    void updateVehicleStatus(String vehicleId, VehicleStatus vehicleStatus);

    List<DriverHistory> getDriverHistory(String vehicleId);

    AssignedVehicle getCurrentlyAssignedVehicle(String driverId);

    List<AssignedVehicle> getVehicleHistory(String driverId);

    void createVehicleFromRequest(VehicleReq vehicle);

    void acceptVehicle(String vehicleId);

    void denyVehicle(String vehicleId, int requestId);

    void addVehicleDocs(String vehicleId, List<VehicleDocumentReq> vehicleDocumentReqs, boolean notAdmin);

    void addVehicleValue(VehicleValueReq vehicleValueReq);

    void updateVehicleValue(VehicleValueUpdateReq vehicleValueUpdateReq);

    void deleteVehicleValue(int vehicleValueId);

    List<VehicleTypeCount> getTypeCount(String ownerId);

    List<VehicleStatusCount> getStatusCount(String ownerId);

    int getTotalVehicle(String ownerId);

    UserBasic getCurrentDriver(String vehicleId);
}
