package com.fa20se28.vma.service;

import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.request.VehicleReq;
import com.fa20se28.vma.request.VehicleUpdateReq;
import com.fa20se28.vma.response.*;

public interface VehicleService {
    int getTotal(int viewOption, String ownerId);

    VehicleTypesRes getTypes();

    BrandRes getBrands();

    VehiclePageRes getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId);

    VehicleDropDownRes getVehiclesDropDown(VehicleDropDownReq request, int pageNum, String ownerId);

    void assignDriverWithVehicle(String vehicleId, String driverId);

    void withdrawIssuedVehicle(String vehicleId);

    void createVehicle(VehicleReq vehicle);

    void deleteVehicle(String vehicleId);

    VehicleDetailRes getVehicleDetails(String vehicleId);

    void updateVehicleDetails(VehicleUpdateReq vehicleUpdateReq);
}
