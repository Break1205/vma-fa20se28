package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Passenger;
import com.fa20se28.vma.request.PassengerReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PassengerMapper {
    @Select("SELECT p.full_name, p.phone_number, p.date_of_birth, p.address " +
            "FROM passenger p " +
            "WHERE p.contract_vehicle_id = #{cv_id} ")
    @Results(id = "passengerLIst", value = {
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "dateOfBirth", column = "date_of_birth"),
    })
    List<Passenger> getPassengersFromContractId(@Param("cv_id") int contractVehicleId);

    @Insert("INSERT INTO passenger " +
            "(contract_vehicle_id, " +
            "full_name," +
            "phone_number, " +
            "date_of_birth, " +
            "address, " +
            "create_date) " +
            "VALUES " +
            "(#{cv_id}, " +
            "#{cvp_list.fullName}, " +
            "#{cvp_list.phoneNumber}, " +
            "#{cvp_list.dateOfBirth}, " +
            "#{cvp_list.address}, " +
            "getdate()) ")
    int createPassenger(
            @Param("cvp_list") PassengerReq passengerReq,
            @Param("cv_id") int contractVehicleId);
}
