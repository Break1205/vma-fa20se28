package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleMiscComponent;
import com.fa20se28.vma.configuration.exception.DataExecutionException;
import com.fa20se28.vma.mapper.BrandMapper;
import com.fa20se28.vma.mapper.VehicleSeatMapper;
import com.fa20se28.vma.mapper.VehicleTypeMapper;
import com.fa20se28.vma.model.Brand;
import com.fa20se28.vma.model.VehicleSeat;
import com.fa20se28.vma.model.VehicleType;
import com.fa20se28.vma.request.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class VehicleMiscComponentImpl implements VehicleMiscComponent {
    private final VehicleTypeMapper vehicleTypeMapper;
    private final BrandMapper brandMapper;
    private final VehicleSeatMapper vehicleSeatMapper;

    public VehicleMiscComponentImpl(VehicleTypeMapper vehicleTypeMapper, BrandMapper brandMapper, VehicleSeatMapper vehicleSeatMapper) {
        this.vehicleTypeMapper = vehicleTypeMapper;
        this.brandMapper = brandMapper;
        this.vehicleSeatMapper = vehicleSeatMapper;
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
    public List<VehicleSeat> getSeats() {
        return vehicleSeatMapper.getSeats();
    }

    @Override
    @Transactional
    public void createBrand(BrandReq brandReq) {
        int row = brandMapper.createBrand(brandReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Brand creation failed!");
        }

    }

    @Override
    @Transactional
    public void updateBrand(BrandUpdateReq brandUpdateReq) {
        int row = brandMapper.updateBrand(brandUpdateReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Brand update failed!");
        }
    }

    @Override
    @Transactional
    public void createType(VehicleTypeReq vehicleTypeReq) {
        int row = vehicleTypeMapper.createType(vehicleTypeReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Type creation failed!");
        }
    }

    @Override
    @Transactional
    public void updateType(VehicleTypeUpdateReq vehicleTypeUpdateReq) {
        int row = vehicleTypeMapper.updateType(vehicleTypeUpdateReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Type update failed!");
        }
    }

    @Override
    @Transactional
    public void createSeat(SeatsReq seatsReq) {
        int row = vehicleSeatMapper.createSeats(seatsReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Seats creation failed!");
        }
    }

    @Override
    @Transactional
    public void updateSeat(SeatsReq seatsReq) {
        int row = vehicleSeatMapper.updateSeats(seatsReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Seats update failed!");
        }
    }

    @Override
    public List<Integer> getSeatsList() {
        return vehicleSeatMapper.getSeatNumbers();
    }


}
