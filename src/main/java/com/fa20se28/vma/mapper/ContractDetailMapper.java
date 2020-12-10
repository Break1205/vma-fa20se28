package com.fa20se28.vma.mapper;

import com.fa20se28.vma.request.ContractTripReq;
import com.fa20se28.vma.request.ContractTripUpdateReq;
import org.apache.ibatis.annotations.*;

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
    @Options(keyProperty = "cd_request.contractDetailId", useGeneratedKeys = true)
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
            "contract_detail_id = #{cd_request.contractTripId} ")
    int updateContractDetail(@Param("cd_request") ContractTripUpdateReq contractTripUpdateReq);

    @Select("SELECT TOP 1 contract_detail_id " +
            "FROM contract_detail " +
            "WHERE " +
            "contract_id = #{c_id} " +
            "ORDER BY create_date DESC ")
    int getContractDetailId(@Param("c_id") int contractId);
}
