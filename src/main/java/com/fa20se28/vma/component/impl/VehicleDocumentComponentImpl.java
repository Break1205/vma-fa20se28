package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleDocumentComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.configuration.exception.ResourceIsInUsedException;
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
            vdoc.setImageLinks(vehicleDocumentImageMapper.getImageLinks(vdoc.getVehicleDocumentId(), 0));
        }
        return vehicleDocuments;
    }

    @Override
    @Transactional
    public void createVehicleDocument(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq, boolean notAdmin) {
        if (!vehicleDocumentMapper.isDocumentExist(vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId())) {
            int docRow = vehicleDocumentMapper.createVehicleDocument(vehicleDocumentStandaloneReq.getVehicleDocumentReq(), vehicleDocumentStandaloneReq.getVehicleId(), notAdmin);

            addDocImages(docRow, vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId(), vehicleDocumentStandaloneReq.getVehicleDocumentReq().getImageLinks());
        } else {
            throw new DataException("Document with ID " + vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId() + " already exist!");
        }
    }

    @Override
    @Transactional
    public void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq) {
        int row = vehicleDocumentMapper.updateVehicleDocument(vehicleDocumentUpdateReq);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        } else {
            for (VehicleDocumentImage image : vehicleDocumentUpdateReq.getImageLinks()) {
                int imageRow = vehicleDocumentImageMapper.updateVehicleDocumentImage(vehicleDocumentUpdateReq.getVehicleDocumentId(), image);
                if (imageRow == 0) {
                    throw new DataException("Unknown error occurred. Data not modified!");
                }
            }
        }
    }

    @Override
    @Transactional
    public void deleteDocument(String vehicleDocId) {
        int row = vehicleDocumentMapper.updateDocumentStatus(vehicleDocId, true);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void createVehicleDocumentFromRequest(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq) {
        String documentId = vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId();

        if (!vehicleDocumentMapper.isDocumentExist(documentId)) {
            createVehicleDocument(vehicleDocumentStandaloneReq, true);
        } else {
            if (!vehicleDocumentMapper.checkIfDocumentIsInUse(documentId)) {
                throw new ResourceIsInUsedException("A document with id " + documentId + " is still valid");
            } else {
                int docRow = vehicleDocumentMapper.updateVehicleDocument(
                        new VehicleDocumentUpdateReq(
                                vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId(),
                                vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentType(),
                                vehicleDocumentStandaloneReq.getVehicleDocumentReq().getRegisteredLocation(),
                                vehicleDocumentStandaloneReq.getVehicleDocumentReq().getRegisteredDate(),
                                vehicleDocumentStandaloneReq.getVehicleDocumentReq().getExpiryDate()));

                addDocImages(docRow, vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId(), vehicleDocumentStandaloneReq.getVehicleDocumentReq().getImageLinks());
            }
        }
    }

    @Override
    public VehicleDocument getVehicleDocument(String vehicleDocId) {
        VehicleDocument vehicleDocument = vehicleDocumentMapper.getVehicleDocumentById(vehicleDocId);
        vehicleDocument.setImageLinks(vehicleDocumentImageMapper.getImageLinks(vehicleDocument.getVehicleDocumentId(), 0));

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
        int deleteImageRow = 0;

        List<VehicleDocumentImage> vehicleDocumentImages = vehicleDocumentImageMapper.getImageLinks(vehicleDocument.getVehicleDocumentId(), 0);
        for (VehicleDocumentImage image : vehicleDocumentImages) {
            vehicleDocumentImageMapper.moveDeniedVehicleDocumentImageToLog(image.getVehicleDocumentImageId(), requestId);
            vehicleDocumentImageMapper.deleteImages(image.getVehicleDocumentImageId());
            deleteImageRow++;
            moveImageRow++;
        }

        if (moveDocRow == 0 || moveImageRow == 0 || deleteImageRow == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void addDocImages(int resultRow, String vehicleDocId, List<String> images) {
        if (resultRow == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        } else {
            for (String image : images) {
                int imageRow = vehicleDocumentImageMapper.createVehicleDocumentImage(vehicleDocId, image);

                if (imageRow == 0) {
                    throw new DataException("Unknown error occurred. Data not modified!");
                }
            }
        }
    }


}
