package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Vehicle;
import com.fa20se28.vma.model.VehicleStatus;
import com.fa20se28.vma.model.VehicleType;

import java.util.List;

public interface VehicleComponent {
    int getTotal(int viewOption, String ownerId);

    List<VehicleType> getTypes();

    List<VehicleStatus> getStatus();

    List<Vehicle> getVehicles(String vehicleId, String model, String vehicleType, float vehicleDisMin, float vehicleDisMax , String vehicleStatus, int viewOption, int pageNum, String ownerId);
}
