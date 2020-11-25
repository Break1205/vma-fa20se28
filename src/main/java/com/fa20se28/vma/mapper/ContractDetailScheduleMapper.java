package com.fa20se28.vma.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    @Delete("DELETE " +
            "FROM contract_detail_schedule " +
            "WHERE contract_detail_id = #{cd_id} ")
    int deleteContractSchedule(@Param("cd_id") int contractDetailId);
}
