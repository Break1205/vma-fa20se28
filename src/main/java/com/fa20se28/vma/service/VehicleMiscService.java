package com.fa20se28.vma.service;

import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.BrandRes;
import com.fa20se28.vma.response.SeatsRes;
import com.fa20se28.vma.response.VehicleTypesRes;

public interface VehicleMiscService {
    VehicleTypesRes getTypes();

    BrandRes getBrands();

    SeatsRes getSeats();

    void createBrand(BrandReq brandReq);

    void updateBrand(BrandUpdateReq brandUpdateReq);

    void createType(VehicleTypeReq vehicleTypeReq);

    void updateType(VehicleTypeUpdateReq vehicleTypeUpdateReq);

    void createSeats(SeatsReq seatsReq);

    void updateSeats(SeatsReq seatsReq);
}
