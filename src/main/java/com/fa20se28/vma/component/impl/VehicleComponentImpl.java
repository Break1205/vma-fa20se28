package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.configuration.exception.DataException;
import com.fa20se28.vma.configuration.exception.ResourceIsInUsedException;
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

            if (assignRow == 0 || updateStatusRow == 0) {
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

            if (withDrawRow == 0 || updateStatusRow == 0 || createPlaceHolderRow == 0) {
                throw new DataException("Unknown error occurred. Data not modified!");
            }
        }
    }

    @Override
    @Transactional
    public void createVehicle(VehicleReq vehicle, boolean notAdmin) {
        if (!vehicleMapper.isVehicleExist(vehicle.getVehicleId())) {
            int vehicleRow;
            if (!notAdmin) {
                vehicleRow = vehicleMapper.createVehicle(vehicle, VehicleStatus.AVAILABLE_NO_DRIVER);
            } else {
                vehicleRow = vehicleMapper.createVehicle(vehicle, VehicleStatus.PENDING_APPROVAL);
            }
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

            if (vehicleRow == 0 || documentRowCount == 0 || documentImageRowCount == 0) {
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

    @Override
    @Transactional
    public void createVehicleFromRequest(VehicleReq vehicle) {
        if (!vehicleMapper.isVehicleExist(vehicle.getVehicleId())) {
            createVehicle(vehicle, true);
        } else {
            if (vehicleMapper.getVehicleStatus(vehicle.getVehicleId()) != VehicleStatus.PENDING_APPROVAL
                && vehicleMapper.getVehicleStatus(vehicle.getVehicleId()) != VehicleStatus.REJECTED
                    && vehicleMapper.getVehicleStatus(vehicle.getVehicleId()) != VehicleStatus.DELETED) {
                throw new ResourceIsInUsedException("Vehicle is already approved!");
            }

            int updateVehicleRow = vehicleMapper.updateVehicle(
                    new VehicleUpdateReq(vehicle.getVehicleId(), vehicle.getVehicleTypeId(), vehicle.getBrandId(), vehicle.getOwnerId(),
                            VehicleStatus.PENDING_APPROVAL, vehicle.getSeats(), vehicle.getImageLink(), vehicle.getModel(),
                            vehicle.getOrigin(), vehicle.getChassisNumber(), vehicle.getEngineNumber(),
                            vehicle.getYearOfManufacture(), vehicle.getDistanceDriven()));

            int documentRowCount = 0;
            int documentImageRowCount = 0;

            if (updateVehicleRow != 0) {
                for (VehicleDocumentReq doc : vehicle.getVehicleDocuments()) {
                    int vehicleDoc;
                    if (!vehicleDocumentMapper.isDocumentExist(doc.getVehicleDocumentId())) {
                        vehicleDoc = vehicleDocumentMapper.createVehicleDocument(doc, vehicle.getVehicleId(), false);
                    } else {
                        vehicleDoc = vehicleDocumentMapper.updateVehicleDocument(
                                new VehicleDocumentUpdateReq(
                                        doc.getVehicleDocumentId(),
                                        doc.getVehicleDocumentType(),
                                        doc.getRegisteredLocation(),
                                        doc.getRegisteredDate(),
                                        doc.getExpiryDate()));
                    }

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
                    }
                }
            }

            if (updateVehicleRow == 0 || documentRowCount == 0 || documentImageRowCount == 0) {
                throw new DataException("Unknown error occurred. Data not added!");
            }
        }
    }

    @Override
    @Transactional
    public void acceptVehicle(String vehicleId) {
        issuedVehicleMapper.createPlaceholder(vehicleId);
        int vehicleStatusRow = vehicleMapper.updateVehicleStatus(vehicleId, VehicleStatus.AVAILABLE_NO_DRIVER);

        if (vehicleStatusRow ==  0) {
            throw new DataException("Unknown error occurred. Data not added!");
        }
    }

    @Override
    @Transactional
    public void denyVehicle(String vehicleId, int requestId) {
        int vehicleStatusUpdateRow = vehicleMapper.updateVehicleStatus(vehicleId, VehicleStatus.REJECTED);

        if (vehicleStatusUpdateRow == 0) {
            throw new DataException("Unknown error occurred. Data not added!");
        }

        VehicleDetail vehicleDetail = vehicleMapper.getVehicleDetails(vehicleId);
        int vehicleLogRow = vehicleMapper.moveDeniedVehicleToLog(vehicleDetail, requestId);

        if (vehicleLogRow == 0) {
            throw new DataException("Unknown error occurred. Data not added!");
        } else {
            for (VehicleDocument doc: vehicleDocumentMapper.getVehicleDocuments(vehicleId, 1)) {
                int moveDocRow = vehicleDocumentMapper.moveDeniedVehicleDocumentToLog(doc, vehicleId, requestId);
                int moveImageRow = 0;
                int deleteImageRow = 0;

                List<VehicleDocumentImage> vehicleDocumentImages = vehicleDocumentImageMapper.getImageLinks(doc.getVehicleDocumentId(), 0);
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
        }
    }
}
