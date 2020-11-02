package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContractMapper {
    @Select("SELECT \n" +
            "contract_id,\n" +
            "contract_status \n" +
            "FROM contract \n" +
            "WHERE contract_owner_id = '${contract_owner_id}' \n" +
            "AND contract_status != 'FINISHED'")
    @Results(id = "unfinishedContractResult", value = {
            @Result(property = "contractId",column = "contract_id"),
            @Result(property = "contractStatus",column = "contract_status")
    })
    List<Contract> getUnfinishedContractByContractOwnerId(@Param("contract_owner_id") String contractOwnerId);
}
