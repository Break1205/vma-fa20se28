package com.fa20se28.vma.component;

import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.request.VehicleReq;
import com.fa20se28.vma.request.VehicleUpdateReq;

import java.util.List;

public interface VehicleComponent {
    int getTotal(int viewOption, String ownerId);

    List<VehicleType> getTypes();

    List<Brand> getBrands();

    List<Vehicle> getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId);

    List<VehicleDropDown> getVehiclesDropDown(VehicleDropDownReq request, int pageNum, String ownerId);

    void assignVehicle(String vehicleId, String driverId);

    void withdrawVehicle(String vehicleId);

    void createVehicle(VehicleReq vehicle);

    void deleteVehicle(String vehicleId);

    VehicleDetail getVehicleDetails(String vehicleId);

    void updateVehicleDetails(VehicleUpdateReq vehicleUpdateReq);
}
