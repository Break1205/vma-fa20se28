package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.VehicleMiscComponent;
import com.fa20se28.vma.request.BrandReq;
import com.fa20se28.vma.request.BrandUpdateReq;
import com.fa20se28.vma.request.VehicleTypeReq;
import com.fa20se28.vma.request.VehicleTypeUpdateReq;
import com.fa20se28.vma.response.BrandRes;
import com.fa20se28.vma.response.VehicleTypesRes;
import com.fa20se28.vma.service.VehicleMiscService;
import org.springframework.stereotype.Service;

@Service
public class VehicleMiscServiceImpl implements VehicleMiscService {
    private final VehicleMiscComponent vehicleMiscComponent;

    public VehicleMiscServiceImpl(VehicleMiscComponent vehicleMiscComponent) {
        this.vehicleMiscComponent = vehicleMiscComponent;
    }

    @Override
    public VehicleTypesRes getTypes() {
        return new VehicleTypesRes(vehicleMiscComponent.getTypes());
    }

    @Override
    public BrandRes getBrands() {
        return new BrandRes(vehicleMiscComponent.getBrands());
    }

    @Override
    public void createBrand(BrandReq brandReq) {
        vehicleMiscComponent.createBrand(brandReq);
    }

    @Override
    public void updateBrand(BrandUpdateReq brandUpdateReq) {
        vehicleMiscComponent.updateBrand(brandUpdateReq);
    }

    @Override
    public void createType(VehicleTypeReq vehicleTypeReq) {
        vehicleMiscComponent.createType(vehicleTypeReq);
    }

    @Override
    public void updateType(VehicleTypeUpdateReq vehicleTypeUpdateReq) {
        vehicleMiscComponent.updateType(vehicleTypeUpdateReq);
    }
}
