package com.fa20se28.vma.service;

import com.fa20se28.vma.request.VehicleDocumentUpdateReq;
import com.fa20se28.vma.response.VehicleDocumentRes;

public interface VehicleDocumentService {
    VehicleDocumentRes getVehicleDocuments(String vehicleId);

    void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq);
}
