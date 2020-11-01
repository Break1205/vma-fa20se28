package com.fa20se28.vma.service;

import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.request.VehicleReq;
import com.fa20se28.vma.response.VehicleDetailRes;
import com.fa20se28.vma.response.VehicleDropDownRes;
import com.fa20se28.vma.response.VehiclePageRes;
import com.fa20se28.vma.response.VehicleTypesRes;

public interface VehicleService {
    int getTotal(int viewOption, String ownerId);

    VehicleTypesRes getTypes();

    VehiclePageRes getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId);

    VehicleDropDownRes getVehiclesDropDown(VehicleDropDownReq request, int pageNum, String status, String ownerId);

    void assignDriverWithVehicle(String vehicleId, String driverId);

    void withdrawIssuedVehicle(String vehicleId);

    void createVehicle(VehicleReq vehicle);

    void deleteVehicle(String vehicleId);

    VehicleDetailRes getVehicleDetails(String vehicleId);
}
