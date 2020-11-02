package com.fa20se28.vma.component;

import com.fa20se28.vma.model.VehicleDocument;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;

import java.util.List;

public interface VehicleDocumentComponent {
    List<VehicleDocument> getVehicleDocuments(String vehicleDocId);

    void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq);
}
