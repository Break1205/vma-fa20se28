package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.UserStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserStatusMapper {

    @Select("SELECT us.[user_status_id], us.[user_status_name] " +
            "FROM [user_status] us ")
    @Results
    List<UserStatus> getStatusList();
}
