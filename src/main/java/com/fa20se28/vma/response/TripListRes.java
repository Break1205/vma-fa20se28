package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Trip;

import java.util.List;

public class TripListRes {
    private List<Trip> tripList;

    public TripListRes(List<Trip> tripList) {
        this.tripList = tripList;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(List<Trip> tripList) {
        this.tripList = tripList;
    }
}
