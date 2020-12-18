package com.fa20se28.vma.enums;

public enum NotificationType {
    REQUEST_ACCEPTED("Request Accepted"), // mobile
    REQUEST_DENIED("Request Denied"), // mobile
    CONTRACT_STARTED("Contract Started"), // web
    CONTRACT_COMPLETED("Contract Completed"), // web
    CONTRACT_ASSIGNED("Contract Assigned"), // mobile
    CONTRACT_DROPPED("Contract Dropped"), // mobile
    START_TRIP("Start Trip"), // web
    END_TRIP("End Trip"), // web
    LICENSE_EXPIRED("License Expired"), // mobile
    NEW_REQUEST("New Request"), // web
    VEHICLE_CHANGED("Vehicle Changed"); // mobile

    private String name;

    NotificationType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
