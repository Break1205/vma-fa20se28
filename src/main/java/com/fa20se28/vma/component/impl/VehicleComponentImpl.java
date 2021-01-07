package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.configuration.exception.DataExecutionException;
import com.fa20se28.vma.configuration.exception.InvalidStatusException;
import com.fa20se28.vma.configuration.exception.ResourceIsInUsedException;
import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.mapper.*;
import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class VehicleComponentImpl implements VehicleComponent {
    private final VehicleMapper vehicleMapper;
    private final IssuedVehicleMapper issuedVehicleMapper;
    private final VehicleDocumentMapper vehicleDocumentMapper;
    private final VehicleDocumentImageMapper vehicleDocumentImageMapper;
    private final VehicleValueMapper vehicleValueMapper;
    private final ContractVehicleMapper contractVehicleMapper;
    private final VehicleOwnerMapper vehicleOwnerMapper;

    public VehicleComponentImpl(
            VehicleMapper vehicleMapper,
            IssuedVehicleMapper issuedVehicleMapper,
            VehicleDocumentMapper vehicleDocumentMapper,
            VehicleDocumentImageMapper vehicleDocumentImageMapper,
            VehicleValueMapper vehicleValueMapper, ContractVehicleMapper contractVehicleMapper, VehicleOwnerMapper vehicleOwnerMapper) {
        this.vehicleMapper = vehicleMapper;
        this.issuedVehicleMapper = issuedVehicleMapper;
        this.vehicleDocumentMapper = vehicleDocumentMapper;
        this.vehicleDocumentImageMapper = vehicleDocumentImageMapper;
        this.vehicleValueMapper = vehicleValueMapper;
        this.contractVehicleMapper = contractVehicleMapper;
        this.vehicleOwnerMapper = vehicleOwnerMapper;
    }

    @Override
    public int getTotal(VehiclePageReq request, int useStatus, String ownerId) {
        return vehicleMapper.getTotal(request, useStatus, ownerId);
    }

    @Override
    public List<Vehicle> getVehicles(VehiclePageReq request, int useStatus, int pageNum, String ownerId, int takeAll) {
        return vehicleMapper.getVehicles(request, useStatus, pageNum * 15, ownerId, takeAll);
    }

    @Override
    @Transactional
    public void assignVehicle(String vehicleId, String driverId) {
        if (vehicleMapper.getVehicleStatus(vehicleId) != VehicleStatus.AVAILABLE_NO_DRIVER) {
            throw new InvalidStatusException("Vehicle is already occupied or is currently unavailable!");
        } else {
            String ownerId = vehicleOwnerMapper.getCurrentOwnerId(vehicleId);
            int assignRow = issuedVehicleMapper.updateIssuedVehicle(vehicleId, driverId, ownerId, 0);
            int updateStatusRow = vehicleMapper.updateVehicleStatus(vehicleId, VehicleStatus.AVAILABLE);

            if (assignRow == 0 || updateStatusRow == 0) {
                throw new DataExecutionException("Unknown error occurred. Driver assignment failed!");
            }
        }
    }

    @Override
    @Transactional
    public void withdrawVehicle(String vehicleId) {
        if (contractVehicleMapper.checkIfThereIsRemainingTrip(issuedVehicleMapper.getCurrentIssuedVehicleId(vehicleId), LocalDateTime.now())) {
            throw new ResourceIsInUsedException("Vehicle still has remaining trip(s)!");
        } else {
            int withDrawRow = issuedVehicleMapper.updateIssuedVehicle(vehicleId, null, null, 1);
            String ownerId = vehicleOwnerMapper.getCurrentOwnerId(vehicleId);
            int createPlaceHolderRow = issuedVehicleMapper.createPlaceholder(vehicleId, ownerId);
            int updateStatusRow = vehicleMapper.updateVehicleStatus(vehicleId, VehicleStatus.AVAILABLE_NO_DRIVER);

            if (withDrawRow == 0 || createPlaceHolderRow == 0 || updateStatusRow == 0) {
                throw new DataExecutionException("Unknown error occurred. Data not modified!");
            }
        }
    }

    @Override
    @Transactional
    public void createVehicle(VehicleReq vehicle, boolean notAdmin) {
        if (vehicleMapper.isVehicleExist(vehicle.getVehicleId())) {
            throw new ResourceIsInUsedException("Vehicle with ID " + vehicle.getVehicleId() + " already exist!");
        } else {
            int vehicleRow;
            int placeholderRow;

            if (!notAdmin) {
                vehicleRow = vehicleMapper.createVehicle(vehicle, VehicleStatus.AVAILABLE_NO_DRIVER);
                placeholderRow = issuedVehicleMapper.createPlaceholder(vehicle.getVehicleId(), vehicle.getOwnerId());
            } else {
                vehicleRow = vehicleMapper.createVehicle(vehicle, VehicleStatus.PENDING_APPROVAL);
                placeholderRow = 1;
            }

            int ownerRow = vehicleOwnerMapper.createVehicleOwner(vehicle.getOwnerId(), vehicle.getVehicleId(), LocalDate.now());

            if (vehicleRow != 0) {
                if (vehicle.getVehicleDocuments() != null) {
                    addVehicleDocs(vehicle.getVehicleId(), vehicle.getVehicleDocuments(), false);
                }
                if (vehicle.getVehicleValue() != null) {
                    addVehicleValue(vehicle.getVehicleValue());
                }
            }

            if (vehicleRow == 0 || placeholderRow == 0 || ownerRow == 0) {
                throw new DataExecutionException("Unknown error occurred. Vehicle creation failed!");
            }
        }
    }

    @Override
    @Transactional
    public void deleteVehicle(String vehicleId) {
        if (vehicleMapper.getVehicleStatus(vehicleId) != VehicleStatus.AVAILABLE_NO_DRIVER) {
            throw new ResourceIsInUsedException("Vehicle still has driver occupied!");
        } else {
            int vehicleRow = vehicleMapper.deleteVehicle(vehicleId);
            int documentRow = vehicleDocumentMapper.deleteVehicleDocuments(vehicleId);
            int deleteOwnerRow = vehicleOwnerMapper.updateVehicleOwner(vehicleId, LocalDate.now());

            if (vehicleRow == 0 || documentRow == 0 || deleteOwnerRow == 0) {
                throw new DataExecutionException("Unknown error occurred. Vehicle deletion failed!");
            }
        }
    }

    @Override
    public VehicleDetail getVehicleDetails(String vehicleId) {
        return vehicleMapper.getVehicleDetails(vehicleId);
    }

    @Override
    @Transactional
    public void updateVehicleDetails(VehicleUpdateReq vehicleUpdateReq) {
        int updateOwnerRow;
        int createOwnerRow;

        if (!vehicleOwnerMapper.getCurrentOwnerId(vehicleUpdateReq.getVehicleId()).equals(vehicleUpdateReq.getOwnerId())) {
            if (contractVehicleMapper.checkIfThereIsRemainingTrip(issuedVehicleMapper.getCurrentIssuedVehicleId(vehicleUpdateReq.getVehicleId()), LocalDateTime.now())) {
                throw new ResourceIsInUsedException("Vehicle still has remaining trip(s). Unable to update ownership!");
            } else {
                updateOwnerRow = vehicleOwnerMapper.updateVehicleOwner(vehicleUpdateReq.getVehicleId(), LocalDate.now());
                createOwnerRow = vehicleOwnerMapper.createVehicleOwner(vehicleUpdateReq.getOwnerId(), vehicleUpdateReq.getVehicleId(), LocalDate.now());
            }
        } else {
            updateOwnerRow = 1;
            createOwnerRow = 1;
        }

        int vehicleUpdateRow = vehicleMapper.updateVehicle(vehicleUpdateReq);

        if (vehicleUpdateRow == 0 || updateOwnerRow == 0 || createOwnerRow == 0) {
            throw new DataExecutionException("Unknown error occurred. Vehicle update failed!");
        }
    }

    @Override
    @Transactional
    public void updateVehicleStatus(String vehicleId, VehicleStatus vehicleStatus) {
        int row = vehicleMapper.updateVehicleStatus(vehicleId, vehicleStatus);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Vehicle status update failed!");
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
    public List<AssignedVehicle> getVehicleHistory(String driverId) {
        return issuedVehicleMapper.getVehicleHistory(driverId);
    }

    @Override
    @Transactional
    public void createVehicleFromRequest(VehicleReq vehicle) {
        if (!vehicleMapper.isVehicleExist(vehicle.getVehicleId())) {
            createVehicle(vehicle, true);
        } else {
            VehicleStatus status = vehicleMapper.getVehicleStatus(vehicle.getVehicleId());

            if (status != VehicleStatus.PENDING_APPROVAL && status != VehicleStatus.REJECTED && status != VehicleStatus.DELETED) {
                throw new ResourceIsInUsedException("Vehicle is already approved!");
            }

            int updateVehicleRow = vehicleMapper.updateVehicle(
                    new VehicleUpdateReq(vehicle.getVehicleId(), vehicle.getVehicleTypeId(), vehicle.getBrandId(), vehicle.getOwnerId(),
                            vehicle.getSeatsId(), vehicle.getImageLink(), vehicle.getModel(),
                            vehicle.getOrigin(), vehicle.getChassisNumber(), vehicle.getEngineNumber(),
                            vehicle.getYearOfManufacture(), vehicle.getDistanceDriven()));

            int createOwnerRow = vehicleOwnerMapper.createVehicleOwner(vehicle.getOwnerId(), vehicle.getVehicleId(), LocalDate.now());

            int updateStatusRow = vehicleMapper.updateVehicleStatus(vehicle.getVehicleId(), VehicleStatus.PENDING_APPROVAL);

            if (updateVehicleRow != 0) {
                addVehicleDocs(vehicle.getVehicleId(), vehicle.getVehicleDocuments(), true);
                addVehicleValue(vehicle.getVehicleValue());
            }

            if (updateVehicleRow == 0 || createOwnerRow == 0 || updateStatusRow == 0) {
                throw new DataExecutionException("Unknown error occurred. Vehicle creation from request failed!");
            }
        }
    }

    @Override
    @Transactional
    public void acceptVehicle(String vehicleId) {
        String ownerId = vehicleOwnerMapper.getCurrentOwnerId(vehicleId);
        int placeholderRow = issuedVehicleMapper.createPlaceholder(vehicleId, ownerId);
        int vehicleStatusRow = vehicleMapper.updateVehicleStatus(vehicleId, VehicleStatus.AVAILABLE_NO_DRIVER);
        int acceptDocRow = vehicleDocumentMapper.changeVehicleDocumentsStatusFromRequest(vehicleId, DocumentStatus.VALID);

        if (placeholderRow == 0 || vehicleStatusRow == 0 || acceptDocRow == 0) {
            throw new DataExecutionException("Unknown error occurred. Vehicle approval failed!");
        }
    }

    @Override
    @Transactional
    public void denyVehicle(String vehicleId) {
        int statusUpdateRow = vehicleMapper.updateVehicleStatus(vehicleId, VehicleStatus.REJECTED);
        int deleteAllValues = vehicleValueMapper.deleteAllValues(vehicleId);
        int denyDocRow = vehicleDocumentMapper.changeVehicleDocumentsStatusFromRequest(vehicleId, DocumentStatus.REJECTED);
        int deleteOwnerRow = vehicleOwnerMapper.updateVehicleOwner(vehicleId, LocalDate.now());

        if (statusUpdateRow == 0 || deleteAllValues == 0 || denyDocRow == 0 || deleteOwnerRow == 0) {
            throw new DataExecutionException("Unknown error occurred. Vehicle denial failed!");
        }
    }

    @Override
    @Transactional
    public void addVehicleDocs(String vehicleId, List<VehicleDocumentReq> vehicleDocuments, boolean notAdmin) {
        for (VehicleDocumentReq doc : vehicleDocuments) {
            int vehicleDoc;
            if (vehicleDocumentMapper.isDocumentExist(doc.getVehicleDocumentNumber())) {
                throw new ResourceIsInUsedException("Document with ID " + doc.getVehicleDocumentNumber() + " already exist!");
            } else {
                if (!notAdmin) {
                    vehicleDoc = vehicleDocumentMapper.createVehicleDocument(doc, vehicleId, DocumentStatus.VALID);
                } else {
                    vehicleDoc = vehicleDocumentMapper.createVehicleDocument(doc, vehicleId, DocumentStatus.PENDING);
                }
            }

            if (vehicleDoc == 0) {
                throw new DataExecutionException("Unknown error occurred. One or more vehicle document creation failed !");
            } else {
                if (doc.getImageLinks() != null) {
                    for (String image : doc.getImageLinks()) {
                        int vehicleDocImage = vehicleDocumentImageMapper.createVehicleDocumentImage(doc.getVehicleDocumentId(), image);

                        if (vehicleDocImage == 0) {
                            throw new DataExecutionException("Unknown error occurred! Image creation failed!");
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void addVehicleValue(VehicleValueReq vehicleValueReq) {
        int row = vehicleValueMapper.createValue(vehicleValueReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void updateVehicleValue(VehicleValueUpdateReq vehicleValueUpdateReq) {
        int row = vehicleValueMapper.updateValue(vehicleValueUpdateReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void deleteVehicleValue(int vehicleValueId) {
        int row = vehicleValueMapper.deleteValue(vehicleValueId);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    public List<VehicleTypeCount> getTypeCount(String ownerId) {
        return vehicleMapper.getTypeCount(ownerId);
    }

    @Override
    public List<VehicleStatusCount> getStatusCount(String ownerId) {
        List<VehicleStatusCount> statusCounts = vehicleMapper.getStatusCount(ownerId);

        List<VehicleStatus> missingStatus = Stream.of(VehicleStatus.values()).collect(Collectors.toList());
        missingStatus.removeAll(vehicleMapper.getStatusInFleet(ownerId));

        for (VehicleStatus status : missingStatus) {
            statusCounts.add(new VehicleStatusCount(status.toString(), 0));
        }

        return statusCounts;
    }

    @Override
    public int getTotalVehicle(String ownerId) {
        return vehicleMapper.getTotalVehicle(ownerId);
    }

    @Override
    public UserBasic getCurrentDriver(String vehicleId) {
        return issuedVehicleMapper.getAssignedDriver(vehicleId);
    }
}
