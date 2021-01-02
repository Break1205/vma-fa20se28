package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleDocumentComponent;
import com.fa20se28.vma.configuration.exception.DataExecutionException;
import com.fa20se28.vma.configuration.exception.ResourceIsInUsedException;
import com.fa20se28.vma.enums.DocumentStatus;
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
    public List<VehicleDocument> getVehicleDocuments(String vehicleId, int useStatus, DocumentStatus documentStatus) {
        if (useStatus == 0) {
            return vehicleDocumentMapper.getVehicleDocuments(vehicleId, useStatus, DocumentStatus.VALID);
        } else {
            return vehicleDocumentMapper.getVehicleDocuments(vehicleId, useStatus, documentStatus);
        }
    }

    @Override
    @Transactional
    public void createVehicleDocument(VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq, boolean notAdmin) {
        if (vehicleDocumentMapper.isDocumentExist(vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentNumber()) && !notAdmin) {
            throw new ResourceIsInUsedException("Document number " + vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentNumber() + " already exist!");
        } else {
            int docRow;

            if (!notAdmin) {
                docRow = vehicleDocumentMapper.createVehicleDocument(vehicleDocumentStandaloneReq.getVehicleDocumentReq(), vehicleDocumentStandaloneReq.getVehicleId(), DocumentStatus.VALID);
            } else {
                docRow = vehicleDocumentMapper.createVehicleDocument(vehicleDocumentStandaloneReq.getVehicleDocumentReq(), vehicleDocumentStandaloneReq.getVehicleId(), DocumentStatus.PENDING);
            }

            addDocImages(docRow, vehicleDocumentStandaloneReq.getVehicleDocumentReq().getVehicleDocumentId(), vehicleDocumentStandaloneReq.getVehicleDocumentReq().getImageLinks());
        }
    }

    @Override
    @Transactional
    public void updateVehicleDocument(VehicleDocumentUpdateReq vehicleDocumentUpdateReq) {
        String documentNumber = vehicleDocumentMapper.getVehicleDocumentById(vehicleDocumentUpdateReq.getVehicleDocumentId()).getVehicleDocumentNumber();

        if (!documentNumber.equals(vehicleDocumentUpdateReq.getVehicleDocumentNumber())) {
            if (vehicleDocumentMapper.isDocumentExist(vehicleDocumentUpdateReq.getVehicleDocumentNumber())) {
                throw new ResourceIsInUsedException("Document number " + vehicleDocumentUpdateReq.getVehicleDocumentNumber() + " already exist!");
            }
        }

        int row = vehicleDocumentMapper.updateVehicleDocument(vehicleDocumentUpdateReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Vehicle document update failed!");
        } else {
            if (vehicleDocumentUpdateReq.getImageLinks() != null) {
                for (VehicleDocumentImage image : vehicleDocumentUpdateReq.getImageLinks()) {
                    int imageRow = vehicleDocumentImageMapper.updateVehicleDocumentImage(vehicleDocumentUpdateReq.getVehicleDocumentId(), image);
                    if (imageRow == 0) {
                        throw new DataExecutionException("Unknown error occurred. Image update failed!");
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void deleteDocument(int vehicleDocId) {
        int row = vehicleDocumentMapper.updateDocumentStatus(vehicleDocId, DocumentStatus.DELETED);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Vehicle document deletion failed!");
        }
    }

    @Override
    public VehicleDocument getVehicleDocument(int vehicleDocId) {
        return vehicleDocumentMapper.getVehicleDocumentById(vehicleDocId);
    }

    @Override
    @Transactional
    public void acceptDocument(int vehicleDocId) {
        int updateOldRow;

        VehicleDocument currentDoc = vehicleDocumentMapper.getVehicleDocumentById(vehicleDocId);
        if (vehicleDocumentMapper.isDocumentExist(currentDoc.getVehicleDocumentNumber())) {
            updateOldRow = vehicleDocumentMapper.updateDocumentStatus(vehicleDocumentMapper.getCurrentIdOfVehicleDocument(currentDoc.getVehicleDocumentNumber()), DocumentStatus.OUTDATED);
        } else {
            updateOldRow = 1;
        }

        int acceptRow = vehicleDocumentMapper.updateDocumentStatus(vehicleDocId, DocumentStatus.VALID);

        if (updateOldRow == 0 || acceptRow == 0) {
            throw new DataExecutionException("Unknown error occurred. Vehicle document approval failed!");
        }
    }

    @Override
    @Transactional
    public void denyDocument(int vehicleDocId) {
        int row = vehicleDocumentMapper.updateDocumentStatus(vehicleDocId, DocumentStatus.REJECTED);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Vehicle document denial failed!");
        }
    }

    @Override
    @Transactional
    public void addDocImages(int resultRow, int documentId, List<String> images) {
        if (resultRow == 0) {
            throw new DataExecutionException("Unknown error occurred. Document creation failed!");
        } else {
            for (String image : images) {
                int imageRow = vehicleDocumentImageMapper.createVehicleDocumentImage(documentId, image);

                if (imageRow == 0) {
                    throw new DataExecutionException("Unknown error occurred. Image creation failed!");
                }
            }
        }
    }


}
