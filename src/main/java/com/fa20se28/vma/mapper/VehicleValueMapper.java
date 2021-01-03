package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.VehicleValue;
import com.fa20se28.vma.request.VehicleValueReq;
import com.fa20se28.vma.request.VehicleValueUpdateReq;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
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
            "SET is_deleted = '1' " +
            "WHERE " +
            "vehicle_value_id = #{vv_id} ")
    int deleteValue(@Param("vv_id") int vehicleValueId);

    @Update("UPDATE vehicle_value " +
            "SET is_deleted = '1' " +
            "WHERE " +
            "vehicle_id = #{v_id} " +
            "AND is_deleted = '0' ")
    int deleteAllValues(@Param("v_id") String vehicleId);

    @Select("SELECT " +
            "vehicle_value_id, " +
            "value, " +
            "start_date," +
            "end_date, " +
            "is_deleted " +
            "FROM vehicle_value " +
            "WHERE vehicle_id = #{v_id} " +
            "AND start_date < #{vv_to} " +
            "AND end_date >= #{vv_from} " +
            "AND is_deleted = 0 " +
            "ORDER BY create_date DESC ")
    @Results(id = "valuesInTimeframe", value = {
            @Result(property = "vehicleValueId", column = "vehicle_value_id"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "isDeleted", column = "is_deleted")
    })
    List<VehicleValue> getValueInTimeframe(
            @Param("v_id") String vehicleId,
            @Param("vv_from") LocalDate fromDate,
            @Param("vv_to") LocalDate toDate);
}
