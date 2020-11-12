package com.fa20se28.vma.request;

import com.fa20se28.vma.enums.ContractVehicleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TripListReq {
    private int issuedVehicleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date departureTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date destinationTime;
    private ContractVehicleStatus vehicleStatus;

    public TripListReq(int issuedVehicleId, Date departureTime, Date destinationTime, ContractVehicleStatus vehicleStatus) {
        this.issuedVehicleId = issuedVehicleId;
        this.departureTime = departureTime;
        this.destinationTime = destinationTime;
        this.vehicleStatus = vehicleStatus;
    }

    public int getIssuedVehicleId() {
        return issuedVehicleId;
    }

    public void setIssuedVehicleId(int issuedVehicleId) {
        this.issuedVehicleId = issuedVehicleId;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(Date destinationTime) {
        this.destinationTime = destinationTime;
    }

    public ContractVehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(ContractVehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
