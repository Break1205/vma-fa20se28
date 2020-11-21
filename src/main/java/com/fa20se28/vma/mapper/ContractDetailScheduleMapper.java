package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.ContractTripSchedule;
import com.fa20se28.vma.request.ContractTripScheduleUpdateReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
            "contract_detail_schedule_id = #{cdc_request.contractDetailScheduleId} ")
    int updateContractSchedule(@Param("cdc_request") ContractTripScheduleUpdateReq contractTripScheduleUpdateReq);

    @Select("SELECT " +
            "contract_detail_schedule_id, location " +
            "FROM contract_detail_schedule " +
            "WHERE " +
            "contract_detail_id = #{cd_id} ")
    @Results(id = "tripSchedule", value = {
            @Result(property = "tripScheduleId", column = "contract_detail_schedule_id"),
    })
    List<ContractTripSchedule> getContractTripSchedule(@Param("cd_id") int contractDetailId);
}
