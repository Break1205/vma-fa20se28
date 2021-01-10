package com.fa20se28.vma.request;

public class VehicleContractAutoReq {
    private VehicleContractReq request;
    private int vehicleCount;
    private int passengerCount;

    public VehicleContractAutoReq(VehicleContractReq request, int vehicleCount, int passengerCount) {
        this.request = request;
        this.vehicleCount = vehicleCount;
        this.passengerCount = passengerCount;
    }

    public VehicleContractReq getRequest() {
        return request;
    }

    public void setRequest(VehicleContractReq request) {
        this.request = request;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }
}
