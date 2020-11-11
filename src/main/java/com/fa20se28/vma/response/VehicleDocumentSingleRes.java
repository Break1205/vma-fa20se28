package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleDocument;

public class VehicleDocumentSingleRes {
    private VehicleDocument vehicleDocument;

    public VehicleDocumentSingleRes(VehicleDocument vehicleDocument) {
        this.vehicleDocument = vehicleDocument;
    }

    public VehicleDocument getVehicleDocument() {
        return vehicleDocument;
    }

    public void setVehicleDocument(VehicleDocument vehicleDocument) {
        this.vehicleDocument = vehicleDocument;
    }
}
