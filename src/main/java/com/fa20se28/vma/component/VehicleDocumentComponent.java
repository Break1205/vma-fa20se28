package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.model.VehicleDocument;
import com.fa20se28.vma.request.VehicleDocumentStandaloneReq;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;

import java.util.List;

public interface VehicleDocumentComponent {
    List<VehicleDocument> getVehicleDocuments(String vehicleId, int useStatus, DocumentStatus documentStatus);

    void createVehicleDocument(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq, boolean notAdmin);

    void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq);

    void deleteDocument(int vehicleDocId);

    VehicleDocument getVehicleDocument(int vehicleDocId);

    void acceptDocument(int vehicleDocId);

    void denyDocument(int vehicleDocId);

    void addDocImages(int resultRow, int documentId, List<String> images);
}
