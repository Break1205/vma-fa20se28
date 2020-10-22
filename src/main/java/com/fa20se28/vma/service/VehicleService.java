package com.fa20se28.vma.service;

import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.response.VehicleDropDownRes;
import com.fa20se28.vma.response.VehiclePageRes;
import com.fa20se28.vma.response.VehicleStatusRes;
import com.fa20se28.vma.response.VehicleTypesRes;

public interface VehicleService {
    int getTotal(int viewOption, String ownerId);

    VehicleTypesRes getTypes();

    VehicleStatusRes getStatus();

    VehiclePageRes getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId);

    VehicleDropDownRes getAvailableVehicles(VehicleDropDownReq request, int pageNum, String ownerId);

    void assignDriverWithVehicle(String vehicleId, String driverId);

    void withdrawIssuedVehicle(String vehicleId);
}
