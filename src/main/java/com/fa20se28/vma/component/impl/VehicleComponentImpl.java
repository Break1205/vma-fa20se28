package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.mapper.*;
import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class VehicleComponentImpl implements VehicleComponent {
    private final VehicleMapper vehicleMapper;
    private final IssuedVehicleMapper issuedVehicleMapper;
    private final VehicleDocumentMapper vehicleDocumentMapper;
    private final VehicleDocumentImageMapper vehicleDocumentImageMapper;

    public VehicleComponentImpl(
            VehicleMapper vehicleMapper,
            IssuedVehicleMapper issuedVehicleMapper,
            VehicleDocumentMapper vehicleDocumentMapper,
            VehicleDocumentImageMapper vehicleDocumentImageMapper) {
        this.vehicleMapper = vehicleMapper;
        this.issuedVehicleMapper = issuedVehicleMapper;
        this.vehicleDocumentMapper = vehicleDocumentMapper;
        this.vehicleDocumentImageMapper = vehicleDocumentImageMapper;
    }

    @Override
    public int getTotal(VehiclePageReq request, int viewOption, String ownerId) {
        return vehicleMapper.getTotal(request, viewOption, ownerId);
    }


    @Override
    public List<Vehicle> getVehicles(VehiclePageReq request, int viewOption, int pageNum, String ownerId, int takeAll) {
        return vehicleMapper.getVehicles(request, viewOption, pageNum * 15, ownerId, takeAll);
    }

    @Override
    public List<VehicleDropDown> getVehiclesDropDown(VehicleDropDownReq request, int pageNum, String ownerId) {
        return vehicleMapper.getVehicleDropDownByStatus(request, pageNum, ownerId);
    }

    @Override
    @Transactional
    public void assignVehicle(String vehicleId, String driverId) {
        if (vehicleMapper.getVehicleStatus(vehicleId) != VehicleStatus.AVAILABLE_NO_DRIVER) {
            throw new DataException("Vehicle is unavailable!");
        } else {
            int assignRow = issuedVehicleMapper.updateIssuedVehicle(vehicleId, driverId, 0);
            int updateStatusRow = vehicleMapper.updateVehicleStatus(vehicleId, VehicleStatus.AVAILABLE);

            if (assignRow == 0 && updateStatusRow == 0) {
                throw new DataException("Unknown error occurred. Data not added!");
            }
        }
    }

    @Override
    @Transactional
    public void withdrawVehicle(String vehicleId) {
        if (vehicleMapper.getVehicleStatus(vehicleId) != VehicleStatus.AVAILABLE) {
            throw new DataException("Vehicle is still occupied!");
        } else {
            int withDrawRow = issuedVehicleMapper.updateIssuedVehicle(vehicleId, null, 1);
            int createPlaceHolderRow = issuedVehicleMapper.createPlaceholder(vehicleId);
            int updateStatusRow = vehicleMapper.updateVehicleStatus(vehicleId, VehicleStatus.AVAILABLE_NO_DRIVER);

            if (withDrawRow == 0 && updateStatusRow == 0 && createPlaceHolderRow == 0) {
                throw new DataException("Unknown error occurred. Data not modified!");
            }
        }
    }

    @Override
    @Transactional
    public void createVehicle(VehicleReq vehicle) {
        if (!vehicleMapper.isVehicleExist(vehicle.getVehicleId())) {
            int vehicleRow = vehicleMapper.createVehicle(vehicle, VehicleStatus.AVAILABLE_NO_DRIVER);
            int documentRowCount = 0;
            int documentImageRowCount = 0;

            if (vehicleRow != 0) {
                for (VehicleDocumentReq doc : vehicle.getVehicleDocuments()) {
                    if (!vehicleDocumentMapper.isDocumentExist(doc.getVehicleDocumentId())) {
                        int vehicleDoc = vehicleDocumentMapper.createVehicleDocument(doc, vehicle.getVehicleId(), false);

                        if (vehicleDoc == 0) {
                            throw new DataException("Unknown error occurred. Data not modified!");
                        } else {
                            for (String image : doc.getImageLinks()) {
                                int vehicleDocImage = vehicleDocumentImageMapper.createVehicleDocumentImage(doc.getVehicleDocumentId(), image);
                                if (vehicleDocImage != 0) {
                                    documentImageRowCount++;
                                }
                            }
                            documentRowCount++;
                            issuedVehicleMapper.createPlaceholder(vehicle.getVehicleId());
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
    @Transactional
    public void deleteVehicle(String vehicleId) {
        if (vehicleMapper.getVehicleStatus(vehicleId) != VehicleStatus.AVAILABLE_NO_DRIVER) {
            throw new DataException("Vehicle is still occupied!");
        } else {
            int row = vehicleMapper.deleteVehicle(vehicleId);

            if (row == 0) {
                throw new DataException("Unknown error occurred. Data not modified!");
            }
        }
    }

    @Override
    public VehicleDetail getVehicleDetails(String vehicleId) {
        VehicleDetail vehicleDetail = vehicleMapper.getVehicleDetails(vehicleId);

        if (issuedVehicleMapper.isVehicleHasDriver(vehicleId)) {
            vehicleDetail.setAssignedDriver(issuedVehicleMapper.getAssignedDriver(vehicleId));
        }

        return vehicleDetail;
    }

    @Override
    @Transactional
    public void updateVehicleDetails(VehicleUpdateReq vehicleUpdateReq) {
        int row = vehicleMapper.updateVehicle(vehicleUpdateReq);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void updateVehicleStatus(String vehicleId, VehicleStatus vehicleStatus) {
        int row = vehicleMapper.updateVehicleStatus(vehicleId, vehicleStatus);

        if (row == 0) {
            throw new DataException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    public List<DriverHistory> getDriverHistory(String vehicleId) {
        return issuedVehicleMapper.getDriverHistory(vehicleId);
    }

    @Override
    public AssignedVehicle getCurrentlyAssignedVehicle(String driverId) {
        return issuedVehicleMapper.getCurrentlyAssignedVehicleByDriverId(driverId);
    }
}
