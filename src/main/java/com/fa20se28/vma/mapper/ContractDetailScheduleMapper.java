package com.fa20se28.vma.mapper;

import com.fa20se28.vma.request.ContractTripScheduleUpdateReq;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ContractDetailScheduleMapper {
    @Insert("INSERT INTO contract_detail_schedule " +
            "(contract_detail_id," +
            "[location]," +
            "create_date) " +
            "VALUES " +
            "(#{cd_id}, " +
            "#{cdc_location}, " +
            "getDate()) ")
    int createContractSchedule(
            @Param("cdc_location") String location,
            @Param("cd_id") int contractDetailId);

    @Update("UPDATE contract_detail_schedule " +
            "SET " +
            "[location] = #{cdc_request.location}  " +
            "WHERE " +
            "contract_detail_schedule_id = #{cdc_request.locationId} ")
    int updateContractSchedule(@Param("cdc_request") ContractTripScheduleUpdateReq contractTripScheduleUpdateReq);

    @Delete("DELETE " +
            "FROM contract_detail_schedule " +
            "WHERE contract_detail_schedule_id = #{l_id} ")
    int deleteContractSchedule(@Param("l_id") int locationId);
}
