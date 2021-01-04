package com.fa20se28.vma.enums;

public enum NotificationType {
    /*
    Mobile
    */
    REQUEST_ACCEPTED("Request Accepted"),
    REQUEST_DENIED("Request Denied"),
    CONTRACT_ASSIGNED("Contract Assigned"),
    CONTRACT_DROPPED("Contract Dropped"),
    LICENSE_EXPIRED("License Expired"),
    VEHICLE_CHANGED("Vehicle Changed"),
    EMERGENCY_ASSISTED("Emergency Assisted"),
    /*
    Mobile
     */

    /*
    Web
     */
    CONTRACT_STARTED("Contract Started"),
    CONTRACT_COMPLETED("Contract Completed"),
    START_TRIP("Start Trip"),
    END_TRIP("End Trip"),
    NEW_REQUEST("New Request");
    /*
    Web
     */


    private String name;

    NotificationType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
