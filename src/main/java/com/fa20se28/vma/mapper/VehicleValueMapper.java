package com.fa20se28.vma.mapper;

import com.fa20se28.vma.request.VehicleValueReq;
import com.fa20se28.vma.request.VehicleValueUpdateReq;
import org.apache.ibatis.annotations.*;

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
            "end_date = #{vv_req.endDate}, " +
            "WHERE maintenance_id = #{vv_req.vehicleValueId} ")
    int updateValue(@Param("vv_req") VehicleValueUpdateReq vehicleValueUpdateReq);

    @Update("UPDATE vehicle_value " +
            "SET " +
            "is_deleted = '1' " +
            "WHERE " +
            "vehicle_value_id = #{vv_id} ")
    int deleteValue(int valueId);

    @Select("SELECT TOP 1 vehicle_value_id " +
            "FROM vehicle_value " +
            "WHERE is_deleted = '0' " +
            "ORDER BY create_date DESC ")
    int getCurrentValueId();

}
