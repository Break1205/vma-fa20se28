package com.fa20se28.vma.component;

import com.fa20se28.vma.model.VehicleDocument;
import com.fa20se28.vma.request.VehicleDocumentStandaloneReq;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;

import java.util.List;

public interface VehicleDocumentComponent {
    List<VehicleDocument> getVehicleDocuments(String vehicleDocId, int viewOption);

    void createVehicleDocument(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq, int roleId);

    void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq);

    void deleteDocument(String vehicleDocId);

    void createVehicleDocumentFromRequest(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq);

    VehicleDocument getVehicleDocument(String vehicleDocId);
}
