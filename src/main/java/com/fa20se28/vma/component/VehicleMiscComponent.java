package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Brand;
import com.fa20se28.vma.model.VehicleType;
import com.fa20se28.vma.request.BrandReq;
import com.fa20se28.vma.request.BrandUpdateReq;
import com.fa20se28.vma.request.VehicleTypeReq;
import com.fa20se28.vma.request.VehicleTypeUpdateReq;

import java.util.List;

public interface VehicleMiscComponent {
    List<VehicleType> getTypes();

    List<Brand> getBrands();

    void createBrand(BrandReq brandReq);

    void updateBrand(BrandUpdateReq brandUpdateReq);

    void createType(VehicleTypeReq vehicleTypeReq);

    void updateType(VehicleTypeUpdateReq vehicleTypeUpdateReq);
}
