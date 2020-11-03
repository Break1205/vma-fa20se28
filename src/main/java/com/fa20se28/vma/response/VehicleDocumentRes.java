package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleDocument;

import java.util.List;

public class VehicleDocumentRes {
    private List<VehicleDocument> vehicleDocuments;

    public VehicleDocumentRes(List<VehicleDocument> vehicleDocuments) {
        this.vehicleDocuments = vehicleDocuments;
    }

    public List<VehicleDocument> getVehicleDocuments() {
        return vehicleDocuments;
    }

    public void setVehicleDocuments(List<VehicleDocument> vehicleDocuments) {
        this.vehicleDocuments = vehicleDocuments;
    }
}
