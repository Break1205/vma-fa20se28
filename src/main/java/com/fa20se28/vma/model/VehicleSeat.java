package com.fa20se28.vma.model;

public class VehicleSeat {
    private int seatsId;
    private String seatsGroupName;
    private int seats;
    private float pricePerDay;
    private float pricePerDistance;
    private float pricePerHour;

    public String getSeatsGroupName() {
        return seatsGroupName;
    }

    public void setSeatsGroupName(String seatsGroupName) {
        this.seatsGroupName = seatsGroupName;
    }

    public int getSeatsId() {
        return seatsId;
    }

    public void setSeatsId(int seatsId) {
        this.seatsId = seatsId;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public float getPricePerDistance() {
        return pricePerDistance;
    }

    public void setPricePerDistance(float pricePerDistance) {
        this.pricePerDistance = pricePerDistance;
    }

    public float getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(float pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
