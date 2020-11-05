package com.fa20se28.vma.service;

import com.fa20se28.vma.request.VehicleDocumentStandaloneReq;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;
import com.fa20se28.vma.response.VehicleDocumentRes;

public interface VehicleDocumentService {
    VehicleDocumentRes getVehicleDocuments(String vehicleId, int viewOption);

    void createVehicleDocument(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq);

    void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq);

    void deleteDocument(String vehicleDocId);
}
