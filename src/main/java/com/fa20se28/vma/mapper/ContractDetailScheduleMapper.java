package com.fa20se28.vma.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContractDetailScheduleMapper {
    @Insert("INSERT INTO contract_trip_schedule " +
            "(contract_trip_id," +
            "[location]," +
            "create_date) " +
            "VALUES " +
            "(#{cd_id}, " +
            "#{cdc_location}, " +
            "getDate()) ")
    int createContractTripSchedule(
            @Param("cdc_location") String location,
            @Param("cd_id") int contractDetailId);

    @Delete("DELETE cds FROM contract_trip_schedule cds \n" +
            "JOIN contract_trip cd \n" +
            "ON cds.contract_trip_id = cd.contract_trip_id \n" +
            "WHERE cd.contract_id = #{c_id}\n")
    int deleteContractTripSchedule(@Param("c_id") int contractId);
}
