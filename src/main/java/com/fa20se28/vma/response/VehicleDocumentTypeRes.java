package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.VehicleDocumentType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VehicleDocumentTypeRes {
    public List<VehicleDocumentType> getVehicleDocumentTypes() {
        return Stream.of(VehicleDocumentType.values()).collect(Collectors.toList());
    }
}
