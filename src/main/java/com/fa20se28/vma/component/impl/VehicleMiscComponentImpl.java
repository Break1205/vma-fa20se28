package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleMiscComponent;
import com.fa20se28.vma.configuration.exception.DataExecutionException;
import com.fa20se28.vma.mapper.BrandMapper;
import com.fa20se28.vma.mapper.VehicleTypeMapper;
import com.fa20se28.vma.model.Brand;
import com.fa20se28.vma.model.VehicleType;
import com.fa20se28.vma.request.BrandReq;
import com.fa20se28.vma.request.BrandUpdateReq;
import com.fa20se28.vma.request.VehicleTypeReq;
import com.fa20se28.vma.request.VehicleTypeUpdateReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class VehicleMiscComponentImpl implements VehicleMiscComponent {
    private final VehicleTypeMapper vehicleTypeMapper;
    private final BrandMapper brandMapper;

    public VehicleMiscComponentImpl(VehicleTypeMapper vehicleTypeMapper, BrandMapper brandMapper) {
        this.vehicleTypeMapper = vehicleTypeMapper;
        this.brandMapper = brandMapper;
    }

    @Override
    public List<VehicleType> getTypes() {
        return vehicleTypeMapper.getTypes();
    }

    @Override
    public List<Brand> getBrands() {
        return brandMapper.getBrands();
    }

    @Override
    @Transactional
    public void createBrand(BrandReq brandReq) {
        int row = brandMapper.createBrand(brandReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }

    }

    @Override
    @Transactional
    public void updateBrand(BrandUpdateReq brandUpdateReq) {
        int row = brandMapper.updateBrand(brandUpdateReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void createType(VehicleTypeReq vehicleTypeReq) {
        int row = vehicleTypeMapper.createType(vehicleTypeReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void updateType(VehicleTypeUpdateReq vehicleTypeUpdateReq) {
        int row = vehicleTypeMapper.updateType(vehicleTypeUpdateReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }
}
