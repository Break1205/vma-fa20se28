package com.fa20se28.vma.mapper;

import com.fa20se28.vma.entity.Role;
import com.fa20se28.vma.entity.User;
import com.fa20se28.vma.entity.UserStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT [user_id]" +
            "      ,[user_status_id]" +
            "      ,[password]" +
            "      ,[full_name]" +
            "      ,[phone_number]" +
            "      ,[gender]" +
            "      ,[date_of_birth]" +
            "      ,[address]" +
            "      ,[image_link]" +
            "      ,[base_salary]" +
            "  FROM [user]" +
            " WHERE [user_id]=#{user_id}")
    @Results(id = "userResult",value = {
            @Result(property = "userId",column = "user_id"),
            @Result(property = "userStatus",column = "user_status_id",javaType = UserStatus.class,one = @One(select = "findUserStatusById")),
            @Result(property = "password",column = "password"),
            @Result(property = "fullname",column = "full_name"),
            @Result(property = "phoneNumber",column = "phone_number"),
            @Result(property = "gender",column = "gender"),
            @Result(property = "dateOfBirth",column = "date_of_birth"),
            @Result(property = "address",column = "address"),
            @Result(property = "imageLink",column = "image_link"),
            @Result(property = "baseSalary",column = "base_salary")
    })
    User findUserById(@Param(value = "user_id") Long userId);

    @Select("SELECT [user_status_id]" +
            "      ,[user_status_name]" +
            "  FROM [user_status]" +
            "  WHERE [user_status_id] = #{user_status_id}")
    @Results(id= "userStatusResult",value = {
            @Result(property = "userStatusId",column = "user_status_id"),
            @Result(property = "userStatusName",column = "user_status_name")
    })
    UserStatus findUserStatusById(@Param(value = "user_status_id")Long userStatusId);

    @Select("SELECT r.[role_id]" +
            "      ,r.[role_name]" +
            "  FROM [user_roles] ur join [role] r on ur.role_id=r.role_id" +
            "  WHERE [user_id]= #{user_id}")
    @Results(id = "roleResult",value = {
            @Result(property = "roleId",column = "role_id"),
            @Result(property = "roleName", column = "role_name")
    })
    List<Role> findRolesByUserId(@Param(value = "user_id") Long userId);

    @Select("SELECT u.[user_id]" +
            "      ,[user_status_id]" +
            "      ,[password]" +
            "      ,[full_name]" +
            "      ,[phone_number]" +
            "      ,[gender]" +
            "      ,[date_of_birth]" +
            "      ,[address]" +
            "      ,[image_link]" +
            "      ,[base_salary]" +
            "FROM [user] u " +
            "JOIN user_roles ur " +
            "ON u.user_id = ur.user_id join role r " +
            "ON ur.role_id=r.role_id " +
            "WHERE r.role_name= #{role_name}")
    @ResultMap(value = "userResult")
    List<User> findUsersByRole(@Param(value = "role_name") String roleName);

    @Select("Select * from [user]")
    @ResultMap(value = "userResult")
    List<User> findUsers();
}
