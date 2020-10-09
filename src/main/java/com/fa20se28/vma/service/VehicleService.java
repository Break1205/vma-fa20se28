package com.fa20se28.vma.service;

import com.fa20se28.vma.response.VehiclePageRes;
import org.springframework.stereotype.Service;

public interface VehicleService {
    int getTotal(int viewOption, String ownerId);

    VehiclePageRes getVehicles(String vehicleId, String model, String vehicleType, Float vehicleDisMin, Float vehicleDisMax , String vehicleStatus, int viewOption, int pageNum, String ownerId);
}
