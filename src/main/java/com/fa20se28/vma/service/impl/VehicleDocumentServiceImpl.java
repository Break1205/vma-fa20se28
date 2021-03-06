package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.VehicleDocumentComponent;
import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.request.VehicleDocumentStandaloneReq;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;
import com.fa20se28.vma.response.VehicleDocumentRes;
import com.fa20se28.vma.response.VehicleDocumentSingleRes;
import com.fa20se28.vma.service.VehicleDocumentService;
import org.springframework.stereotype.Service;

@Service
public class VehicleDocumentServiceImpl implements VehicleDocumentService {
    private final VehicleDocumentComponent vehicleDocumentComponent;

    public VehicleDocumentServiceImpl(VehicleDocumentComponent vehicleDocumentComponent) {
        this.vehicleDocumentComponent = vehicleDocumentComponent;
    }

    @Override
    public VehicleDocumentRes getVehicleDocuments(String vehicleId, int useStatus, DocumentStatus documentStatus) {
        return new VehicleDocumentRes(vehicleDocumentComponent.getVehicleDocuments(vehicleId, useStatus, documentStatus));
    }

    @Override
    public VehicleDocumentSingleRes getVehicleDocument(int vehicleDocId) {
        return new VehicleDocumentSingleRes(vehicleDocumentComponent.getVehicleDocument(vehicleDocId));
    }

    @Override
    public void createVehicleDocument(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq) {
        vehicleDocumentComponent.createVehicleDocument(vehicleDocumentStandaloneReq, false);
    }

    @Override
    public void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq) {
        vehicleDocumentComponent.updateVehicleDocument(vehicleDocumentUpdateReq);
    }

    @Override
    public void deleteDocument(int vehicleDocId) {
        vehicleDocumentComponent.deleteDocument(vehicleDocId);
    }
}
