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

    @Delete("DELETE cds FROM contract_detail_schedule cds \n" +
            "JOIN contract_detail cd \n" +
            "ON cds.contract_detail_id = cd.contract_detail_id \n" +
            "WHERE cd.contract_id = #{c_id}\n")
    int deleteContractSchedule(@Param("c_id") int contractId);
}
