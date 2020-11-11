package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleDocumentComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.mapper.VehicleDocumentImageMapper;
import com.fa20se28.vma.mapper.VehicleDocumentMapper;
import com.fa20se28.vma.model.VehicleDocument;
import com.fa20se28.vma.model.VehicleDocumentImage;
import com.fa20se28.vma.request.VehicleDocumentStandaloneReq;
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
    public List<VehicleDocument> getVehicleDocuments(String vehicleId, int viewOption) {
        List<VehicleDocument> vehicleDocuments = vehicleDocumentMapper.getVehicleDocuments(vehicleId, viewOption);
        for (VehicleDocument vdoc : vehicleDocuments) {
            vdoc.setImageLinks(vehicleDocumentImageMapper.getImageLinks(vdoc.getVehicleDocumentId()));
        }
        return vehicleDocuments;
    }

    @Override
    public void createVehicleDocument(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq) {
        if (!vehicleDocumentMapper.isDocumentExist(vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId())) {
            int row = vehicleDocumentMapper.createVehicleDocument(vehicleDocumentStandaloneReq.getVehicleDocumentReq(), vehicleDocumentStandaloneReq.getVehicleId(), false);

            if (row == 0) {
                throw new DataException("Unknown error occurred. Data not modified!");
            } else {
                for (String image : vehicleDocumentStandaloneReq.getVehicleDocumentReq().getImageLinks()) {
                    vehicleDocumentImageMapper.createVehicleDocumentImage(vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId(), image);
                }
            }
        } else {
            throw new DataException("Document with ID " + vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId() + " already exist!");
        }
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

    @Override
    @Transactional
    public void deleteDocument(String vehicleDocId) {
        int row = vehicleDocumentMapper.updateDocumentStatus(vehicleDocId,  true);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void createVehicleDocumentFromRequest(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq) {
        if (!vehicleDocumentMapper.isDocumentExist(vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId())) {
            int row = vehicleDocumentMapper.createVehicleDocument(vehicleDocumentStandaloneReq.getVehicleDocumentReq(), vehicleDocumentStandaloneReq.getVehicleId(), true);

            if (row == 0) {
                throw new DataException("Unknown error occurred. Data not modified!");
            } else {
                for (String image : vehicleDocumentStandaloneReq.getVehicleDocumentReq().getImageLinks()) {
                    vehicleDocumentImageMapper.createVehicleDocumentImage(vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId(), image);
                }
            }
        }
        else {
            vehicleDocumentImageMapper.deleteImages(vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId());

            int row = vehicleDocumentMapper.updateVehicleDocument(
                    new VehicleDocumentUpdateReq(
                            vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId(),
                            vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentType(),
                            vehicleDocumentStandaloneReq.getVehicleDocumentReq().getRegisteredLocation(),
                            vehicleDocumentStandaloneReq.getVehicleDocumentReq().getRegisteredDate(),
                            vehicleDocumentStandaloneReq.getVehicleDocumentReq().getExpiryDate()));

            if (row == 0) {
                throw new DataException("Unknown error occurred. Data not modified!");
            } else {
                for (String image : vehicleDocumentStandaloneReq.getVehicleDocumentReq().getImageLinks()) {
                    vehicleDocumentImageMapper.createVehicleDocumentImage(vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId(), image);
                }
            }
        }
    }

    @Override
    public VehicleDocument getVehicleDocument(String vehicleDocId) {
        VehicleDocument vehicleDocument = vehicleDocumentMapper.getVehicleDocumentById(vehicleDocId);
        vehicleDocument.setImageLinks(vehicleDocumentImageMapper.getImageLinks(vehicleDocument.getVehicleDocumentId()));

        return vehicleDocument;
    }

    @Override
    @Transactional
    public void acceptDocument(String vehicleDocId) {
        int row = vehicleDocumentMapper.updateDocumentStatus(vehicleDocId, false);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void denyDocument(int requestId, String vehicleId, String vehicleDocId) {
        VehicleDocument vehicleDocument = vehicleDocumentMapper.getVehicleDocumentById(vehicleDocId);

        int moveDocRow = vehicleDocumentMapper.moveDeniedVehicleDocumentToLog(vehicleDocument, vehicleId, requestId);
        int moveImageRow = 0;

        int requestDocLogId = vehicleDocumentMapper.findVehicleDocumentLogByRequestId(requestId);

        List<VehicleDocumentImage> vehicleDocumentImages = vehicleDocumentImageMapper.getImageLinks(vehicleDocument.getVehicleDocumentId());
        for (VehicleDocumentImage image: vehicleDocumentImages) {
            vehicleDocumentImageMapper.moveDeniedVehicleDocumentImage(requestDocLogId, image.getImageLink());
            moveImageRow++;
        }

        if (moveDocRow == 0 && moveImageRow == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }
}
