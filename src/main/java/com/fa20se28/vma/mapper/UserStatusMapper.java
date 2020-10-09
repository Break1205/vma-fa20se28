package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.UserStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserStatusMapper {

    @Select("SELECT us.[user_status_id], us.[user_status_name] " +
            "FROM [user_status] us ")
    @Results(id = "userStatusResult",value = {
            @Result(property = "userStatusId",column = "user_status_id"),
            @Result(property = "userStatusName",column = "user_status_name")
    })
    List<UserStatus> getStatusList();

    @Select("SELECT us.[user_status_id], us.[user_status_name] " +
            "FROM [user_status] us " +
            "WHERE us.[user_status_id] = ${user_status_id} ")
    @ResultMap("userStatusResult")
    UserStatus findStatusById(@Param("user_status_id") Long userStatusId);
}
