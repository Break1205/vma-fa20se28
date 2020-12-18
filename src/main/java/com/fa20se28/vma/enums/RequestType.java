package com.fa20se28.vma.enums;

public enum RequestType {
    NEW_DOCUMENT("New Document"),
    UPDATE_DOCUMENT("Update Document"),
    DELETE_DOCUMENT("Delete Document"),
    NEW_VEHICLE_DOCUMENT("New Vehicle Document"),
    DELETE_VEHICLE_DOCUMENT("Delete Vehicle Document"),
    CHANGE_VEHICLE("Change Vehicle"),
    VEHICLE_NEEDS_REPAIR("Vehicle Needs Repair"),
    NEED_BACKUP_VEHICLE("Need Backup Behicle"),
    ADD_NEW_VEHICLE("Add New Vehicle"),
    WITHDRAW_VEHICLE("Withdraw Vehicle");

    private String name;

    RequestType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
