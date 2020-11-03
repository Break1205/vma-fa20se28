package com.fa20se28.vma.service;

import com.fa20se28.vma.request.BrandReq;
import com.fa20se28.vma.request.BrandUpdateReq;
import com.fa20se28.vma.request.VehicleTypeReq;
import com.fa20se28.vma.request.VehicleTypeUpdateReq;
import com.fa20se28.vma.response.BrandRes;
import com.fa20se28.vma.response.VehicleTypesRes;

public interface VehicleMiscService {
    VehicleTypesRes getTypes();

    BrandRes getBrands();

    void createBrand(BrandReq brandReq);

    void updateBrand(BrandUpdateReq brandUpdateReq);

    void createType(VehicleTypeReq vehicleTypeReq);

    void updateType(VehicleTypeUpdateReq vehicleTypeUpdateReq);
}
