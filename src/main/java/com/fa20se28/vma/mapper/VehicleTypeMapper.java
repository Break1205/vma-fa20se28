package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.VehicleType;
import com.fa20se28.vma.request.VehicleTypeReq;
import com.fa20se28.vma.request.VehicleTypeUpdateReq;
import org.apache.ibatis.annotations.*;

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

    @Insert("INSERT INTO vehicle_type " +
            "(vehicle_type_name) " +
            "VALUES " +
            "(#{v_request.vehicleTypeName}) ")
    int createType(@Param("v_request") VehicleTypeReq vehicleReq);

    @Update("UPDATE vehicle_type " +
            "SET " +
            "vehicle_type_name = #{v_request.vehicleTypeName} " +
            "WHERE vehicle_type_id = #{v_request.vehicleTypeId} ")
    int updateType(@Param("v_request") VehicleTypeUpdateReq vehicleTypeUpdateReq);
}
