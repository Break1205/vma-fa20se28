package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.ContractVehicleComponent;
import com.fa20se28.vma.configuration.CustomUtils;
import com.fa20se28.vma.configuration.exception.DataExecutionException;
import com.fa20se28.vma.configuration.exception.InvalidParamException;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.enums.ContractStatus;
import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.mapper.*;
import com.fa20se28.vma.model.*;
import com.fa20se28.vma.request.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class ContractVehicleComponentImpl implements ContractVehicleComponent {
    private final PassengerMapper passengerMapper;
    private final ContractMapper contractMapper;
    private final ContractVehicleMapper contractVehicleMapper;
    private final IssuedVehicleMapper issuedVehicleMapper;
    private final VehicleMapper vehicleMapper;
    private final VehicleValueMapper vehicleValueMapper;

    public ContractVehicleComponentImpl(PassengerMapper passengerMapper,
                                        ContractMapper contractMapper,
                                        ContractVehicleMapper contractVehicleMapper,
                                        IssuedVehicleMapper issuedVehicleMapper,
                                        VehicleMapper vehicleMapper, VehicleValueMapper vehicleValueMapper) {
        this.passengerMapper = passengerMapper;
        this.contractMapper = contractMapper;
        this.contractVehicleMapper = contractVehicleMapper;
        this.issuedVehicleMapper = issuedVehicleMapper;
        this.vehicleMapper = vehicleMapper;
        this.vehicleValueMapper = vehicleValueMapper;
    }

    @Override
    public List<Passenger> getPassengerList(int contractVehicleId) {
        return passengerMapper.getPassengersFromContractId(contractVehicleId);
    }

    @Override
    @Transactional
    public void createPassengerList(ContractVehiclePassengerReq contractVehiclePassengerReq) {
        for (PassengerReq passengerReq : contractVehiclePassengerReq.getPassengerList()) {
            int row = passengerMapper.createPassenger(passengerReq, contractVehiclePassengerReq.getContractVehicleId());

            if (row == 0) {
                throw new DataExecutionException("Unknown error occurred. Data not modified!");
            }
        }
    }

    @Override
    @Transactional
    public void assignVehicleForContract(ContractVehicleReq contractVehicleReq) {
        Optional<IssuedVehicle> optionalIssuedVehicle =
                issuedVehicleMapper.getCurrentIssuedVehicleWithDriverById(contractVehicleReq.getVehicleId());

        if (optionalIssuedVehicle.isPresent()) {
            if (optionalIssuedVehicle.get().getDriverId() == null) {
                throw new InvalidParamException("Vehicle with id: " + contractVehicleReq.getVehicleId() + " doesn't have any driver");
            }
        } else {
            throw new ResourceNotFoundException("Vehicle with id: " + contractVehicleReq.getVehicleId() + " not found");
        }

        int row = contractVehicleMapper.assignVehicleForContract(
                contractVehicleReq.getContractTripId(),
                optionalIssuedVehicle.get().getIssuedVehicleId(),
                ContractVehicleStatus.NOT_STARTED);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }

    }

    @Override
    public List<VehicleBasic> getContractVehicles(int contractDetailId) {
        return contractVehicleMapper.getContractVehicles(contractDetailId);
    }

    @Override
    public ContractVehicleStatus getVehicleStatus(int contractTripId, int issuedVehicleId) {
        return contractVehicleMapper.getVehicleStatus(contractTripId, issuedVehicleId);
    }

    @Override
    @Transactional
    public void updateContractVehicleStatus(ContractVehicleStatusUpdateReq contractVehicleStatusUpdateReq) {
        int row = contractVehicleMapper.updateContractedVehicleStatus(
                contractVehicleStatusUpdateReq.getContractTripId(),
                issuedVehicleMapper.getCurrentIssuedVehicleId(contractVehicleStatusUpdateReq.getVehicleId()),
                contractVehicleStatusUpdateReq.getVehicleStatus());

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public int startAndEndTrip(TripReq tripReq, boolean option) {
        int contractVehicleRow;
        int vehicleRow;

        VehicleDetail vehicleDetail = vehicleMapper.getVehicleDetails(tripReq.getVehicleId());
        if (vehicleDetail == null) {
            throw new ResourceNotFoundException("Vehicle with id: " + tripReq.getVehicleId() + " not found");
        }
        int currentIssuedId = issuedVehicleMapper.getCurrentIssuedVehicleId(tripReq.getVehicleId());

        ContractDetail detail = contractMapper.getContractDetails(tripReq.getContractId());

        if (!option) {
            if (!vehicleMapper.getVehicleStatus(tripReq.getVehicleId()).equals(VehicleStatus.AVAILABLE)) {
                throw new DataExecutionException("Vehicle is still occupied!");
            } else {
                contractVehicleRow = contractVehicleMapper.updateContractedVehicleStatus(tripReq.getContractTripId(), currentIssuedId, ContractVehicleStatus.IN_PROGRESS);
                vehicleRow = vehicleMapper.updateVehicleStatus(tripReq.getVehicleId(), VehicleStatus.ON_ROUTE);

                if (detail.getContractStatus().equals(ContractStatus.NOT_STARTED)) {
                    startContract(tripReq.getContractId());
                    return 1;
                }
            }
        } else {
            if (!vehicleMapper.getVehicleStatus(tripReq.getVehicleId()).equals(VehicleStatus.ON_ROUTE)) {
                throw new DataExecutionException("Vehicle is not used!");
            } else {
                contractVehicleRow = contractVehicleMapper.updateContractedVehicleStatus(tripReq.getContractTripId(), currentIssuedId, ContractVehicleStatus.COMPLETED);
                vehicleRow = vehicleMapper.updateVehicleStatus(tripReq.getVehicleId(), VehicleStatus.AVAILABLE);
                if (detail.getContractStatus().equals(ContractStatus.IN_PROGRESS)) {
                    List<ContractVehicle> contractVehicles = contractVehicleMapper.getContractVehiclesByContractId(tripReq.getContractId());
                    boolean contractFinished = true;
                    for (ContractVehicle contractVehicle : contractVehicles) {
                        if (!contractVehicle.getContractVehicleStatus().equals(ContractVehicleStatus.COMPLETED)) {
                            contractFinished = false;
                            break;
                        }
                    }
                    if (contractFinished) {
                        completeContract(tripReq.getContractId());
                        return 1;
                    }
                }
            }
        }

        if (contractVehicleRow == 0 || vehicleRow == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }

        return 0;
    }

    @Override
    public List<Trip> getVehicleTrips(TripListReq tripListReq, int page) {
        return contractVehicleMapper.getVehicleTrips(
                tripListReq.getIssuedVehicleId(),
                tripListReq.getVehicleStatus(),
                page * 15, 0);
    }

    @Override
    public List<Trip> getCurrentTrip(int issuedVehicleId) {
        return contractVehicleMapper.getVehicleTrips(issuedVehicleId, ContractVehicleStatus.IN_PROGRESS, 0, 1);
    }

    @Override
    @Transactional
    public void startContract(int contractId) {
        int row = contractMapper.updateStatus(ContractStatus.IN_PROGRESS, contractId);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    @Transactional
    public void completeContract(int contractId) {
        int contractStatusRow = contractMapper.updateStatus(ContractStatus.FINISHED, contractId);

        int completedVehicleCount = contractVehicleMapper.getCompletedVehicleCount(contractId);
        int actualPassengerCount = passengerMapper.getPassengerCountFromContract(contractId);

        int actualNumberRow = contractMapper.updateContractActual(actualPassengerCount, completedVehicleCount, contractId);
        if (contractStatusRow == 0 || actualNumberRow == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }
    }

    @Override
    public List<VehicleContract> getAvailableVehicles(VehicleContractReq vehicleContractReq, int pageNum, int viewOption) {
        //get the first and last date of the inputted start and end datetime.
        LocalDate firstDateOfMonth = YearMonth.from(vehicleContractReq.getStartDate().toLocalDate()).atDay(1);
        LocalDate lastDateOfMonth =  YearMonth.from(vehicleContractReq.getEndDate().toLocalDate()).atEndOfMonth();
        long monthDifferent = ChronoUnit.MONTHS.between(firstDateOfMonth, lastDateOfMonth);

        List<VehicleContract> availableVehicles = contractVehicleMapper.getAvailableVehicles(vehicleContractReq);

        for (VehicleContract vehicle: availableVehicles) {
            //get value of trips in current month with id
            List<VehicleContractValue> vehicleCountAndValues = contractVehicleMapper.getVehicleCountFromContract(vehicle.getVehicleId(), firstDateOfMonth, lastDateOfMonth);

            float earnedValue = 0;
            //calculate the total
            if (vehicleCountAndValues.size() != 0) {
                for (VehicleContractValue contract: vehicleCountAndValues) {
                    int occurrence = contractVehicleMapper.getOccurrence(contract.getContractId(), vehicle.getVehicleId());
                    earnedValue += ((contract.getTotalValue() * 0.25)/contract.getTotalVehicles()) * occurrence;
                }
                vehicle.setCurrentEarnedValue(earnedValue);
            }

            //get vehicle values
            List<VehicleValue> values = vehicleValueMapper.getValueInTimeframe(vehicle.getVehicleId(), firstDateOfMonth, lastDateOfMonth);

            float estimatedValue = 0;
            if (values.size() != 0) {
                for (VehicleValue value: values) {
                    //check if the vehicle have values
                    if (value != null) {
                        if (CustomUtils.isBeforeOrEqualDate(value.getStartDate(), firstDateOfMonth) && CustomUtils.isAfterOrEqualDate(value.getEndDate(), lastDateOfMonth)) {
                            estimatedValue += value.getValue();
                        } else if (CustomUtils.isBeforeOrEqualDate(value.getStartDate(), firstDateOfMonth) && !CustomUtils.isAfterOrEqualDate(value.getEndDate(), lastDateOfMonth)) {
                            estimatedValue += value.getValue()/30 * ChronoUnit.DAYS.between(YearMonth.now().atDay(1), value.getEndDate());
                        } else if (!CustomUtils.isBeforeOrEqualDate(value.getStartDate(), firstDateOfMonth) && CustomUtils.isAfterOrEqualDate(value.getEndDate(), lastDateOfMonth)) {
                            estimatedValue += value.getValue()/30 * (30-ChronoUnit.DAYS.between(YearMonth.now().atDay(1), value.getStartDate()));
                        } else {
                            estimatedValue += value.getValue()/30 * (ChronoUnit.DAYS.between(value.getStartDate(), value.getEndDate()));
                        }
                    }
                }

                if (monthDifferent != 0) {
                    vehicle.setExpectedValue(estimatedValue * (monthDifferent + 1));
                } else {
                    vehicle.setExpectedValue(estimatedValue);
                }
            }

            //check if vehicle value is reached
            if (earnedValue >= estimatedValue && estimatedValue != 0 && viewOption == 0) {
                availableVehicles.remove(vehicle);
            }
        }

        if (pageNum >= 0) {
            if (availableVehicles.size() <= 10 && pageNum == 0) {
                return availableVehicles;
            } else {
                return availableVehicles.subList((10*pageNum) + 1 , (10*pageNum) + 10);
            }
        } else {
            return availableVehicles;
        }
    }

    @Override
    public int getTotalAvailableVehicles(VehicleContractReq vehicleContractReq, int viewOption) {
        return getAvailableVehicles(vehicleContractReq, -1, viewOption).size();
    }
}
