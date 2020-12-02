package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.VehicleValue;
import com.fa20se28.vma.request.VehicleValueReq;
import com.fa20se28.vma.request.VehicleValueUpdateReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleValueMapper {
    @Insert("INSERT INTO vehicle_value " +
            "(vehicle_id, " +
            "value, " +
            "start_date," +
            "end_date, " +
            "create_date, " +
            "is_deleted) " +
            "VALUES " +
            "(#{vv_req.vehicleId}, " +
            "#{vv_req.value}, " +
            "#{vv_req.startDate}, " +
            "#{vv_req.endDate}, " +
            "getDate(), " +
            "0) ")
    int createValue(@Param("vv_req") VehicleValueReq vehicleValueReq);

    @Update("UPDATE vehicle_value " +
            "SET " +
            "value = #{vv_req.value}, " +
            "start_date = #{vv_req.startDate}, " +
            "end_date = #{vv_req.endDate} " +
            "WHERE vehicle_value_id = #{vv_req.vehicleValueId} ")
    int updateValue(@Param("vv_req") VehicleValueUpdateReq vehicleValueUpdateReq);

    @Update("UPDATE vehicle_value " +
            "SET " +
            "is_deleted = '1' " +
            "WHERE " +
            "vehicle_value_id = #{vv_id} ")
    int deleteValue(int vehicleValueId);

    @Select("SELECT TOP 1 vehicle_value_id " +
            "FROM vehicle_value " +
            "WHERE is_deleted = '0' " +
            "ORDER BY create_date DESC ")
    int getCurrentValueId();

    @Select("SELECT " +
            "vehicle_value_id, " +
            "value, " +
            "start_date," +
            "end_date, " +
            "is_deleted " +
            "FROM vehicle_value " +
            "WHERE vehicle_id = #{v_id} " +
            "ORDER BY create_date DESC ")
    @Results(id = "vehicleValues", value = {
            @Result(property = "vehicleValueId", column = "vehicle_value_id"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "isDeleted", column = "is_deleted")
    })
    List<VehicleValue> getValuesByVehicleId(@Param("v_id") String vehicleId);
}
