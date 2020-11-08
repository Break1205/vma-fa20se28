package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.VehicleStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VehicleStatusRes {
    public List<VehicleStatus> getVehicleStatus() {
        return Stream.of(VehicleStatus.values()).collect(Collectors.toList());
    }
}
