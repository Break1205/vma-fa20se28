package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleDocumentComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.mapper.VehicleDocumentImageMapper;
import com.fa20se28.vma.mapper.VehicleDocumentMapper;
import com.fa20se28.vma.model.VehicleDocument;
import com.fa20se28.vma.model.VehicleDocumentImage;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class VehicleDocumentComponentImpl implements VehicleDocumentComponent {
    private final VehicleDocumentMapper vehicleDocumentMapper;
    private final VehicleDocumentImageMapper vehicleDocumentImageMapper;

    public VehicleDocumentComponentImpl(
            VehicleDocumentMapper vehicleDocumentMapper,
            VehicleDocumentImageMapper vehicleDocumentImageMapper) {
        this.vehicleDocumentMapper = vehicleDocumentMapper;
        this.vehicleDocumentImageMapper = vehicleDocumentImageMapper;
    }

    @Override
    public List<VehicleDocument> getVehicleDocuments(String vehicleId) {
        List<VehicleDocument> vehicleDocuments = vehicleDocumentMapper.getVehicleDocuments(vehicleId);
        for (VehicleDocument vdoc : vehicleDocuments) {
            vdoc.setImageLink(vehicleDocumentImageMapper.getImageLinks(vdoc.getVehicleDocumentId()));
        }
        return vehicleDocuments;
    }

    @Override
    @Transactional
    public void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq) {
        int row = vehicleDocumentMapper.updateVehicleDocument(vehicleDocumentUpdateReq);

        if (row != 0) {
            for (VehicleDocumentImage image : vehicleDocumentUpdateReq.getImageLinks()) {
                vehicleDocumentImageMapper.updateVehicleDocumentImage(vehicleDocumentUpdateReq.getVehicleDocumentId(), image);
            }
        } else {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }
}
