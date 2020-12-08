package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleStatusCount;
import com.fa20se28.vma.model.VehicleTypeCount;

import java.util.List;

public class VehicleOverviewRes {
    private int availableVehicleCount;
    private int onRouteVehicleCount;
    private List<VehicleTypeCount> typeCount;
    private List<VehicleStatusCount> statusCount;

    public VehicleOverviewRes(int availableVehicleCount, int onRouteVehicleCount, List<VehicleTypeCount> typeCount, List<VehicleStatusCount> statusCount) {
        this.availableVehicleCount = availableVehicleCount;
        this.onRouteVehicleCount = onRouteVehicleCount;
        this.typeCount = typeCount;
        this.statusCount = statusCount;
    }

    public int getAvailableVehicleCount() {
        return availableVehicleCount;
    }

    public void setAvailableVehicleCount(int availableVehicleCount) {
        this.availableVehicleCount = availableVehicleCount;
    }

    public int getOnRouteVehicleCount() {
        return onRouteVehicleCount;
    }

    public void setOnRouteVehicleCount(int onRouteVehicleCount) {
        this.onRouteVehicleCount = onRouteVehicleCount;
    }

    public List<VehicleTypeCount> getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(List<VehicleTypeCount> typeCount) {
        this.typeCount = typeCount;
    }

    public List<VehicleStatusCount> getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(List<VehicleStatusCount> statusCount) {
        this.statusCount = statusCount;
    }
}
