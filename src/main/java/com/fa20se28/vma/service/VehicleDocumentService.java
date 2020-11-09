package com.fa20se28.vma.service;

import com.fa20se28.vma.request.VehicleDocumentStandaloneReq;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;
import com.fa20se28.vma.response.VehicleDocumentRes;
import com.fa20se28.vma.response.VehicleDocumentSingleRes;

public interface VehicleDocumentService {
    VehicleDocumentRes getVehicleDocuments(String vehicleId, int viewOption);

    VehicleDocumentSingleRes getVehicleDocument(String vehicleDocId);

    void createVehicleDocument(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq);

    void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq);

    void deleteDocument(String vehicleDocId);
}
