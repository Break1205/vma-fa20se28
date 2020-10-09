package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("Select role_id,role_name from role")
    @Results(id="roleResult", value = {
            @Result(property = "roleId",column = "role_id"),
            @Result(property = "roleName", column = "role_name")
    })
    List<Role> findAll();

    @Select("Select role_id,role_name from role where role_id = #{role_id}")
    @ResultMap(value = "roleResult")
    Role findById(@Param("role_id") Long id);
}