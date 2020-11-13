package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleDetail;

public class VehicleDetailRes {
    private VehicleDetail vehicleDetail;

    public VehicleDetailRes(VehicleDetail vehicleDetail) {
        this.vehicleDetail = vehicleDetail;
    }

    public VehicleDetail getVehicleDetail() {
        return vehicleDetail;
    }

    public void setVehicleDetail(VehicleDetail vehicleDetail) {
        this.vehicleDetail = vehicleDetail;
    }
}
