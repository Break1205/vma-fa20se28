package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Schedule;
import com.fa20se28.vma.model.ScheduleDetail;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReportMapper {
    @Select("SELECT \n" +
            "c.contract_id, \n" +
            "departure_time date,\n" +
            "departure_location,\n" +
            "destination_location,\n" +
            "total_price contract_value,\n" +
            "contract_status\n" +
            "FROM contract c\n" +
            "WHERE departure_time between '${firstDayOfMonth}' AND '${lastDayOfMonth}' \n" +
            "ORDER BY date ASC")
    @Results(id = "scheduleResult", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "contractValue", column = "contract_value"),
            @Result(property = "contractStatus", column = "contract_status"),
            @Result(property = "details", column = "contract_id", many = @Many(select = "getScheduleDetails"))
    })
    List<Schedule> getListSchedule(@Param("firstDayOfMonth") String firstDayOfMonth,
                                   @Param("lastDayOfMonth") String lastDayOfMonth);


    @Select("SELECT \n" +
            "iv.vehicle_id,\n" +
            "u.user_id driver_id,\n" +
            "u.full_name driver_name,\n" +
            "v.owner_id contributor_id\n" +
            "FROM issued_vehicle iv\n" +
            "JOIN vehicle v\n" +
            "ON iv.vehicle_id = v.vehicle_id\n" +
            "JOIN [user] u\n" +
            "ON u.user_id = iv.driver_id \n" +
            "JOIN contract_vehicles cv \n" +
            "ON cv.issued_vehicle_id = iv.issued_vehicle_id \n" +
            "WHERE contract_id = #{contractId}")
    @Results(id = "scheduleDetailResult", value = {
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "driverId", column = "driver_id"),
            @Result(property = "driverName", column = "driver_name"),
            @Result(property = "contributorId", column = "contributor_id")
    })
    List<ScheduleDetail> getScheduleDetails(@Param("contractId") String contractId);
}
