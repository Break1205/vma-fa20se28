package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Passenger;

import java.util.List;

public class PassengerRes {
    private List<Passenger> passengerList;

    public PassengerRes(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }
}
