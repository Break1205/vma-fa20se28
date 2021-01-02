package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleSeat;

import java.util.List;

public class SeatsRes {
    private List<VehicleSeat> seats;

    public SeatsRes(List<VehicleSeat> seats) {
        this.seats = seats;
    }

    public List<VehicleSeat> getSeats() {
        return seats;
    }

    public void setSeats(List<VehicleSeat> seats) {
        this.seats = seats;
    }
}
