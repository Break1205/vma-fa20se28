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

        ContractDetail contract = contractMapper.getContractValueAndNumberOfVehiclesByContractTripId(contractVehicleReq.getContractTripId());

        ContractVehicle contractVehicle = new ContractVehicle(
                contractVehicleReq.getContractTripId(),
                optionalIssuedVehicle.get().getIssuedVehicleId(),
                ContractVehicleStatus.NOT_STARTED,
                -1,
                null,
                contract.getTotalPrice() / contract.getVehicleCount() * 10 / 100,
                contract.getTotalPrice() / contract.getVehicleCount() * 20 / 100);

        int contractVehicleRow = contractVehicleMapper.assignVehicleForContract(contractVehicle);

        if (contractVehicleRow == 0) {
            throw new DataExecutionException("Can not insert Contract Vehicle record");
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
        int row = contractVehicleMapper.updateContractVehicleStatus(
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
                contractVehicleRow = contractVehicleMapper.updateContractVehicleStatus(tripReq.getContractTripId(), currentIssuedId, ContractVehicleStatus.IN_PROGRESS);
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
                contractVehicleRow = contractVehicleMapper.updateContractVehicleStatus(tripReq.getContractTripId(), currentIssuedId, ContractVehicleStatus.COMPLETED);
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
    public List<VehicleContract> getAvailableVehicles(VehicleContractReq vehicleContractReq, int pageNum, int displayAll) {
        // get the first and last date of the inputted start and end date time.
        LocalDate firstDateOfTimeframe = YearMonth.from(vehicleContractReq.getStartDate().toLocalDate()).atDay(1);
        LocalDate lastDateOfTimeframe = YearMonth.from(vehicleContractReq.getEndDate().toLocalDate()).atEndOfMonth();

        // compare the difference in month between the above dates
        long monthDifferent = ChronoUnit.MONTHS.between(firstDateOfTimeframe, lastDateOfTimeframe);

        // get the initial list of available vehicles
        List<VehicleContract> availableVehicles = contractVehicleMapper.getAvailableVehicles(vehicleContractReq);

        //
        for (VehicleContract vehicle : availableVehicles) {
            //get value of trips the vehicle ran in the timeframe
            List<VehicleContractValue> vehicleCountAndValues = contractVehicleMapper.getVehicleCountFromContract(vehicle.getVehicleId(), firstDateOfTimeframe, lastDateOfTimeframe);

            float earnedValue = 0;
            //calculate the total
            if (!vehicleCountAndValues.isEmpty()) {
                for (VehicleContractValue contract : vehicleCountAndValues) {
                    int occurrence = contractVehicleMapper.getOccurrence(contract.getContractId(), vehicle.getVehicleId());
                    earnedValue += ((contract.getTotalValue() * 0.2) / contract.getTotalVehicles()) * occurrence;
                }
                vehicle.setCurrentEarnedValue(earnedValue);
            }

            float estimatedValue;

            // if there is more than one month in the provided timeframe
            if (monthDifferent != 0) {
                float totalValue = 0;

                for (int i = 0; i< monthDifferent; i++) {
                    LocalDate firstDateOfMonth =  firstDateOfTimeframe.plusMonths(i);
                    LocalDate lastDateOfMonth = firstDateOfMonth.withDayOfMonth(firstDateOfMonth.lengthOfMonth());

                    totalValue += calculateEstimatedValue(vehicle.getVehicleId(), firstDateOfMonth, lastDateOfMonth);
                }

                estimatedValue = totalValue;
                vehicle.setExpectedValue(estimatedValue);
            } else {
                estimatedValue = calculateEstimatedValue(vehicle.getVehicleId(), firstDateOfTimeframe, lastDateOfTimeframe);
                vehicle.setExpectedValue(estimatedValue);
            }

            // check if vehicle value is reached
            if (earnedValue >= estimatedValue && estimatedValue != 0 && displayAll == 0) {
                availableVehicles.remove(vehicle);
            }
        }

        if (pageNum >= 0) {
            if (availableVehicles.size() <= 10 && pageNum == 0) {
                return availableVehicles;
            } else {
                return availableVehicles.subList((10 * pageNum) + 1, (10 * pageNum) + 10);
            }
        } else {
            return availableVehicles;
        }
    }

    private float calculateEstimatedValue(String vehicleId, LocalDate firstDateOfMonth, LocalDate lastDateOfMonth) {
        // get vehicle values
        List<VehicleValue> values = vehicleValueMapper.getValueInTimeframe(vehicleId, firstDateOfMonth, lastDateOfMonth);
        int estimatedValue = 0;

        // check if the vehicle have values
        if (!values.isEmpty()) {
            for (VehicleValue value : values) {
                if (CustomUtils.isBeforeOrEqualDate(value.getStartDate(), firstDateOfMonth) && CustomUtils.isAfterOrEqualDate(value.getEndDate(), lastDateOfMonth)) {
                    estimatedValue += value.getValue();
                } else if (CustomUtils.isBeforeOrEqualDate(value.getStartDate(), firstDateOfMonth) && !CustomUtils.isAfterOrEqualDate(value.getEndDate(), lastDateOfMonth)) {
                    estimatedValue += value.getValue() / 30 * ChronoUnit.DAYS.between(YearMonth.now().atDay(1), value.getEndDate());
                } else if (!CustomUtils.isBeforeOrEqualDate(value.getStartDate(), firstDateOfMonth) && CustomUtils.isAfterOrEqualDate(value.getEndDate(), lastDateOfMonth)) {
                    estimatedValue += value.getValue() / 30 * (30 - ChronoUnit.DAYS.between(YearMonth.now().atDay(1), value.getStartDate()));
                } else {
                    estimatedValue += value.getValue() / 30 * (ChronoUnit.DAYS.between(value.getStartDate(), value.getEndDate()));
                }
            }
        }

        return estimatedValue;
    }

    @Override
    public int getTotalAvailableVehicles(VehicleContractReq vehicleContractReq, int displayAll) {
        return getAvailableVehicles(vehicleContractReq, -1, displayAll).size();
    }

    @Override
    public void assignBackUpVehicleForContract(String oldVehicleId, int contractTripId, BackUpVehicleReq backUpVehicleReq) {
        Optional<IssuedVehicle> oldIssuedVehicle = issuedVehicleMapper
                .getCurrentIssuedVehicleWithDriverById(oldVehicleId);
        if (oldIssuedVehicle.isPresent()) {
            if (oldIssuedVehicle.get().getDriverId() == null) {
                throw new InvalidParamException("Vehicle with id: " + oldVehicleId + " doesn't have any driver");
            }
        } else {
            throw new ResourceNotFoundException("Vehicle with id: " + oldVehicleId + " not found");
        }
        ContractVehicle contractVehicle =
                contractVehicleMapper
                        .getContractVehicleByContractTripIdAndIssuedVehicleId(
                                oldIssuedVehicle.get().getIssuedVehicleId(), contractTripId);

        int updateContractVehicleRow =
                contractVehicleMapper.updateContractVehicle(
                        contractVehicle.getContractTripId(),
                        contractVehicle.getIssuedVehicleId(),
                        ContractVehicleStatus.DROPPED,
                        backUpVehicleReq.isFar() ? 1 : 0);
        if (updateContractVehicleRow == 0) {
            throw new DataExecutionException("Can not update Contract Vehicle record");
        }

        int contractVehicleRow = 0;

        float driverMoney = contractVehicle.getDriverMoney();
        float contributorMoney = contractVehicle.getContributorMoney();

        for (String vehicleId : backUpVehicleReq.getVehiclesId()) {
            Optional<IssuedVehicle> newIssuedVehicle = issuedVehicleMapper
                    .getCurrentIssuedVehicleWithDriverById(vehicleId);
            if (newIssuedVehicle.isPresent()) {
                if (oldIssuedVehicle.get().getDriverId() == null) {
                    throw new InvalidParamException("Vehicle with id: " + vehicleId + " doesn't have any driver");
                }
            } else {
                throw new ResourceNotFoundException("Vehicle with id: " + vehicleId + " not found");
            }

            contractVehicle.setIssuedVehicleId(newIssuedVehicle.get().getIssuedVehicleId());
            contractVehicle.setContractVehicleStatus(ContractVehicleStatus.NOT_STARTED);
            contractVehicle.setBackupLocation(backUpVehicleReq.getBrokenVehicleLocation());
            contractVehicle.setDriverMoney(backUpVehicleReq.isFar() ? 0 : driverMoney / backUpVehicleReq.getVehiclesId().size());
            contractVehicle.setContributorMoney(backUpVehicleReq.isFar() ? 0 :contributorMoney / backUpVehicleReq.getVehiclesId().size());

            contractVehicleRow += contractVehicleMapper.assignVehicleForContract(contractVehicle);
        }

        if (contractVehicleRow == 0) {
            throw new DataExecutionException("Can not insert Contract Vehicle record");
        }

    }
}
