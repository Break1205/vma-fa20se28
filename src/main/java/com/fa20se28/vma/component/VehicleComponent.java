package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Vehicle;
import com.fa20se28.vma.model.VehicleDetail;
import com.fa20se28.vma.model.VehicleDropDown;
import com.fa20se28.vma.model.VehicleType;
import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.request.VehicleReq;

import java.util.List;

public interface VehicleComponent {
    int getTotal(int viewOption, String ownerId);

    List<VehicleType> getTypes();

    List<Vehicle> getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId);

    List<VehicleDropDown> getVehiclesDropDown(VehicleDropDownReq request, int pageNum, String status, String ownerId);

    void assignVehicle(String vehicleId, String driverId);

    void withdrawVehicle(String vehicleId);

    void createVehicle(VehicleReq vehicle);

    void deleteVehicle(String vehicleId);

    VehicleDetail getVehicleDetails(String vehicleId);
}
