package com.fa20se28.vma.request;

public class VehicleDocumentStandaloneReq {
    private String vehicleId;
    private int roleId;
    private VehicleDocumentReq vehicleDocumentReq;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public VehicleDocumentReq getVehicleDocumentReq() {
        return vehicleDocumentReq;
    }

    public void setVehicleDocumentReq(VehicleDocumentReq vehicleDocumentReq) {
        this.vehicleDocumentReq = vehicleDocumentReq;
    }
}
