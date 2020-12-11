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
    @Results(id = "passengerList", value = {
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

    @Select("SELECT COUNT (p.passenger_id)\n" +
            "FROM passenger p\n" +
            "JOIN contract_vehicles cv ON p.contract_vehicle_id = cv.contract_vehicle_id \n" +
            "JOIN contract_detail cd ON cv.contract_detail_id = cd.contract_detail_id\n" +
            "JOIN contract c ON c.contract_id = cd.contract_id\n" +
            "WHERE c.contract_id = #{c_id}")
    int getPassengerCountFromContract(@Param("c_id") int contractId);

    @Delete("DELETE \n" +
            "FROM passenger \n" +
            "WHERE contract_vehicle_id = #{cv_id}")
    int deletePassengersFromContractVehicle(@Param("cv_id") int contractVehicleId);
}
