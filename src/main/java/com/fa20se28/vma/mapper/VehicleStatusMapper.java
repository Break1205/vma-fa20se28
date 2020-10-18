package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.VehicleStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VehicleStatusMapper {
    @Select("SELECT vs.vehicle_status_id, vs.vehicle_status_name " +
            "FROM vehicle_status vs ")
    @Results(id = "status", value = {
            @Result(property = "vehicleStatusId", column = "vehicle_status_id"),
            @Result(property = "vehicleStatusName", column = "vehicle_status_name"),
    })
    List<VehicleStatus> getStatus();
}
