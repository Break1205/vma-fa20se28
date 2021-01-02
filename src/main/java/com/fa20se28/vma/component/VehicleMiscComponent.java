package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Brand;
import com.fa20se28.vma.model.VehicleSeat;
import com.fa20se28.vma.model.VehicleType;
import com.fa20se28.vma.request.*;

import java.util.List;

public interface VehicleMiscComponent {
    List<VehicleType> getTypes();

    List<Brand> getBrands();

    List<VehicleSeat> getSeats();

    void createBrand(BrandReq brandReq);

    void updateBrand(BrandUpdateReq brandUpdateReq);

    void createType(VehicleTypeReq vehicleTypeReq);

    void updateType(VehicleTypeUpdateReq vehicleTypeUpdateReq);

    void createSeat(SeatsReq seatsReq);

    void updateSeat(SeatsReq seatsReq);
}
