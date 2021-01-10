package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.VehicleSeat;
import com.fa20se28.vma.request.SeatsReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VehicleSeatMapper {
    @Select("SELECT seats_id, seats_group_name, seats, price_per_day, price_per_distance, price_per_hour " +
            "FROM vehicle_seat ")
    @Results(id = "seatsResult", value = {
            @Result(property = "seatsGroupName", column = "seats_group_name"),
            @Result(property = "seatsId", column = "seats_id"),
            @Result(property = "pricePerDay", column = "price_per_day"),
            @Result(property = "pricePerDistance", column = "price_per_distance"),
            @Result(property = "pricePerHour", column = "price_per_hour"),
    })
    List<VehicleSeat> getSeats();

    @Insert("INSERT INTO vehicle_seat (seats_group_name, seats, price_per_day, price_per_distance, price_per_hour) " +
            "VALUES (#{s_req.seatsGroupName}, #{s_req.seats}, #{s_req.pricePerDay}, #{s_req.pricePerDistance}, #{s_req.pricePerHour}) ")
    int createSeats(@Param("s_req") SeatsReq seatsReq);

    @Update("UPDATE vehicle_seat " +
            "SET " +
            "seats_group_name = #{s_req.seatsGroupName}, " +
            "seats = #{s_req.seats}, " +
            "price_per_day = #{s_req.pricePerDay}, " +
            "price_per_distance = #{s_req.pricePerDistance}, " +
            "price_per_hour = #{s_req.pricePerHour} " +
            "WHERE seats_id = #{s_req.seatsId} ")
    int updateSeats(@Param("s_req") SeatsReq seatsReq);

    @Select("SELECT DISTINCT seats " +
            "FROM vehicle_seat ")
    List<Integer> getSeatNumbers();
}
