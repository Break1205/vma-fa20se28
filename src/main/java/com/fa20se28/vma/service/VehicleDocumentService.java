package com.fa20se28.vma.service;

import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.request.VehicleDocumentStandaloneReq;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;
import com.fa20se28.vma.response.VehicleDocumentRes;
import com.fa20se28.vma.response.VehicleDocumentSingleRes;

public interface VehicleDocumentService {
    VehicleDocumentRes getVehicleDocuments(String vehicleId, int useStatus, DocumentStatus documentStatus);

    VehicleDocumentSingleRes getVehicleDocument(int vehicleDocId);

    void createVehicleDocument(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq);

    void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq);

    void deleteDocument(int vehicleDocId);
}
