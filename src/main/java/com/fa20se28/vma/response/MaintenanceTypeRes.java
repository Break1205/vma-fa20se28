package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.MaintenanceType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaintenanceTypeRes {
    public List<MaintenanceType> getVehicleStatus() {
        return Stream.of(MaintenanceType.values()).collect(Collectors.toList());
    }
}
