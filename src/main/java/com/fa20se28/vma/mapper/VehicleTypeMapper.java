package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.VehicleType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VehicleTypeMapper {
    @Select("SELECT vt.vehicle_type_id, vt.vehicle_type_name " +
            "FROM vehicle_type vt ")
    @Results(id = "types", value = {
            @Result(property = "vehicleTypeId", column = "vehicle_type_id"),
            @Result(property = "vehicleTypeName", column = "vehicle_type_name"),
    })
    List<VehicleType> getTypes();
}
