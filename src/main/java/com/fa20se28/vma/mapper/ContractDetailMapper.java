package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.ContractTrip;
import com.fa20se28.vma.request.ContractTripReq;
import com.fa20se28.vma.request.ContractTripUpdateReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContractDetailMapper {
    @Insert("INSERT INTO contract_detail " +
            "(contract_id," +
            "departure_location, " +
            "departure_time, " +
            "destination_location, " +
            "destination_time," +
            "create_date) " +
            "VALUES " +
            "(#{c_id}, " +
            "#{cd_request.departureLocation}, " +
            "#{cd_request.departureTime}, " +
            "#{cd_request.destinationLocation}, " +
            "#{cd_request.destinationTime}," +
            "getDate()) ")
    int createContractDetail(
            @Param("cd_request") ContractTripReq contractTripReq,
            @Param("c_id") int contractId);

    @Update("UPDATE contract_detail " +
            "SET " +
            "departure_location = #{cd_request.departureLocation},  " +
            "departure_time = #{cd_request.departureTime},  " +
            "destination_location = #{cd_request.destinationLocation},  " +
            "destination_time = #{cd_request.destinationTime}  " +
            "WHERE " +
            "contract_detail_id = #{cd_request.contractDetailId} ")
    int updateContractDetail(@Param("cd_request") ContractTripUpdateReq contractTripUpdateReq);

    @Select("SELECT TOP 1 contract_detail_id " +
            "FROM contract_detail " +
            "WHERE " +
            "contract_id = #{c_id} " +
            "ORDER BY create_date DESC ")
    int getContractDetailId(@Param("c_id") int contractId);

    @Select("SELECT " +
            "contract_detail_id, departure_time, destination_time, departure_location, destination_location " +
            "FROM contract_detail " +
            "WHERE " +
            "contract_id = #{c_id} ")
    @Results(id = "contractTrips", value = {
            @Result(property = "contractTripId", column = "contract_detail_id"),
            @Result(property = "departureLocation", column = "departure_location"),
            @Result(property = "departureTime", column = "departure_time"),
            @Result(property = "destinationLocation", column = "destination_location"),
            @Result(property = "destinationTime", column = "destination_time")
    })
    List<ContractTrip> getContractTrips(@Param("c_id") int contractId);
}
