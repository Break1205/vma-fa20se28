package com.fa20se28.vma.response;

import com.fa20se28.vma.model.VehicleDropDown;

import java.util.List;

public class VehicleDropDownRes {
    private List<VehicleDropDown> dropDownList;

    public VehicleDropDownRes(List<VehicleDropDown> dropDownList) {
        this.dropDownList = dropDownList;
    }

    public List<VehicleDropDown> getDropDownList() {
        return dropDownList;
    }

    public void setDropDownList(List<VehicleDropDown> dropDownList) {
        this.dropDownList = dropDownList;
    }
}
