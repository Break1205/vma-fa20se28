package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.mapper.*;
import com.fa20se28.vma.model.Vehicle;
import com.fa20se28.vma.model.VehicleDetail;
import com.fa20se28.vma.model.VehicleDropDown;
import com.fa20se28.vma.model.VehicleType;
import com.fa20se28.vma.request.VehicleDocumentReq;
import com.fa20se28.vma.request.VehicleDropDownReq;
import com.fa20se28.vma.request.VehiclePageReq;
import com.fa20se28.vma.request.VehicleReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class VehicleComponentImpl implements VehicleComponent {
    private final VehicleMapper vehicleMapper;
    private final VehicleTypeMapper vehicleTypeMapper;
    private final IssuedVehicleMapper issuedVehicleMapper;
    private final VehicleDocumentMapper vehicleDocumentMapper;
    private final VehicleDocumentImageMapper vehicleDocumentImageMapper;

    public VehicleComponentImpl(VehicleMapper vehicleMapper,
                                VehicleTypeMapper vehicleTypeMapper,
                                IssuedVehicleMapper issuedVehicleMapper,
                                VehicleDocumentMapper vehicleDocumentMapper,
                                VehicleDocumentImageMapper vehicleDocumentImageMapper) {
        this.vehicleMapper = vehicleMapper;
        this.vehicleTypeMapper = vehicleTypeMapper;
        this.issuedVehicleMapper = issuedVehicleMapper;
        this.vehicleDocumentMapper = vehicleDocumentMapper;
        this.vehicleDocumentImageMapper = vehicleDocumentImageMapper;
    }

    @Override
    public int getTotal(int viewOption, String ownerId) {
        return vehicleMapper.getTotal(viewOption, ownerId);
    }

    @Override
    public List<VehicleType> getTypes() {
        return vehicleTypeMapper.getTypes();
    }

    @Override
    public List<Vehicle> getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId) {
        return vehicleMapper.getVehicles(request, viewOption, pageNum * 15, ownerId);
    }

    @Override
    public List<VehicleDropDown> getVehiclesDropDown(VehicleDropDownReq request, int pageNum, String status, String ownerId) {
        return vehicleMapper.getAvailableVehicles(request, pageNum, status, ownerId);
    }

    @Override
    @Transactional
    public void assignVehicle(String vehicleId, String driverId) {
        int row = issuedVehicleMapper.assignVehicle(vehicleId, driverId);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not added!");
        }
    }

    @Override
    @Transactional
    public void withdrawVehicle(String vehicleId) {
        int row = issuedVehicleMapper.withdrawVehicle(vehicleId);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void createVehicle(VehicleReq vehicle) {
        if (!vehicleMapper.isVehicleExist(vehicle.getVehicleId())) {
            int vehicleRow;
            if (vehicle.getRoleId() == 2) {
                vehicleRow = vehicleMapper.createVehicle(vehicle, VehicleStatus.PENDING_APPROVAL);
            } else {
                vehicleRow = vehicleMapper.createVehicle(vehicle, VehicleStatus.AVAILABLE_NO_DRIVER);
            }
            int documentRowCount = 0;
            int documentImageRowCount = 0;
            if (vehicleRow != 0) {
                for (VehicleDocumentReq doc : vehicle.getVehicleDocuments()) {
                    if (!vehicleDocumentMapper.isDocumentExist(doc.getVehicleDocumentId())) {
                        int vehicleDoc = vehicleDocumentMapper.createVehicleDocument(doc, vehicle.getVehicleId());
                        if (vehicleDoc != 0) {
                            for (String image : doc.getImageLink()) {
                                int vehicleDocImage = vehicleDocumentImageMapper.createVehicleDocumentImage(doc.getVehicleDocumentId(), image);
                                if (vehicleDocImage != 0) {
                                    documentImageRowCount++;
                                }
                            }
                            documentRowCount++;
                        }
                    } else {
                        throw new DataException("Document with ID " + doc.getVehicleDocumentId() + " already exist!");
                    }
                }
            }

            if (vehicleRow == 0 && documentRowCount == 0 && documentImageRowCount == 0) {
                throw new DataException("Unknown error occurred. Data not added!");
            }
        } else {
            throw new DataException("Vehicle with ID " + vehicle.getVehicleId() + " already exist!");
        }
    }

    @Override
    public void deleteVehicle(String vehicleId) {
        if (vehicleMapper.getVehicleStatus(vehicleId) != VehicleStatus.DELETED) {
            int row = vehicleMapper.deleteVehicle(vehicleId);

            if (row == 0) {
                throw new DataException("Unknown error occurred. Data not modified!");
            }
        } else {
            throw new DataException("Vehicle is already deleted!");
        }
    }

    @Override
    public VehicleDetail getVehicleDetails(String vehicleId) {
        return vehicleMapper.getVehicleDetails(vehicleId);
    }
}
