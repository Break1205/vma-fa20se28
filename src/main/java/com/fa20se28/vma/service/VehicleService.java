package com.fa20se28.vma.service;

import com.fa20se28.vma.response.VehiclePageRes;
import com.fa20se28.vma.response.VehicleStatusRes;
import com.fa20se28.vma.response.VehicleTypesRes;

public interface VehicleService {
    int getTotal(int viewOption, String ownerId);

    VehicleTypesRes getTypes();

    VehicleStatusRes getStatus();

    VehiclePageRes getVehicles(String vehicleId, String model, String vehicleType, Float vehicleDisMin, Float vehicleDisMax , String vehicleStatus, int viewOption, int pageNum, String ownerId);

    VehiclePageRes getAvailableVehicles(String vehicleId, String model, String vehicleType, int pageNum, String ownerId);

    void assignDriverWithVehicle(String vehicleId, String driverId);

    void updateIssuedVehicle(String vehicleId);
}
