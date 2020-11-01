package com.fa20se28.vma.mapper;


import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.request.UserReq;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select("SELECT count(DISTINCT u.user_id)" +
            "FROM [user] u join user_roles ur on u.user_id = ur.user_id where role_id = ${role_id}")
    int findTotalUserByRoles(@Param("role_id") int roleId);

    @Insert("INSERT INTO user_roles " +
            "(user_id, " +
            "role_id) " +
            "VALUES " +
            "(#{user_id}, " +
            "#{role_id} " +
            ");")
    int insertRoleForUserId(@Param("user_id") String userId,
                            @Param("role_id") int roleId);

    @Update("UPDATE [user] " +
            "SET user_status = 'DISABLE' " +
            "WHERE user_id = '${user_id}' ")
    int deleteUserById(@Param("user_id") String userId);


    @Update("Update [user] " +
            "SET user_status = #{user_status} " +
            "WHERE user_id = '${user_id}'")
    int updateUserStatusByUserId(@Param("user_status") UserStatus userStatus,
                                 @Param("user_id") String userId);

    @Select("SELECT " +
            "u.user_id, " +
            "u.password " +
            "FROM [user] u  " +
            "WHERE u.user_id = #{user_id}")
    @Results(id = "userAccountResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "password", column = "password")
    })
    Optional<User> findUserByUserId(@Param("user_id") String userId);

    @Select("SELECT\n" +
            "r.role_id,\n" +
            "r.role_name\n" +
            "FROM user_roles ur\n" +
            "JOIN role r\n" +
            "ON ur.role_id = r.role_id\n" +
            "WHERE ur.user_id = '${user_id}' ")
    @Results(id = "rolesResult", value = {
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "roleName", column = "role_name")
    })
    List<Role> findUserRoles(@Param("user_id") String userId);

    @Insert("INSERT INTO [user]\n" +
            "(user_id,\n" +
            "user_status,\n" +
            "full_name,\n" +
            "password,\n" +
            "phone_number,\n" +
            "gender,\n" +
            "date_of_birth,\n" +
            "address,\n" +
            "image_link,\n" +
            "base_salary,\n" +
            "create_date)\n" +
            "VALUES\n" +
            "(#{userId},\n" +
            "'INACTIVE',\n" +
            "#{fullName},\n" +
            "#{password},\n" +
            "#{phoneNumber},\n" +
            "#{gender},\n" +
            "#{dateOfBirth},\n" +
            "#{address},\n" +
            "#{imageLink},\n" +
            "#{baseSalary},\n" +
            "getDate())")
    int insertUser(UserReq userReq);

    @Update("UPDATE [user] " +
            "SET " +
            "full_name = #{fullName}, " +
            "phone_number = #{phoneNumber}, " +
            "gender = #{gender}, " +
            "date_of_birth = CONVERT(date, #{dateOfBirth}), " +
            "address = #{address}, " +
            "image_link = #{imageLink}, " +
            "base_salary = #{baseSalary} " +
            "WHERE user_id = #{userId}")
    int updateUser(UserReq userReq);
}
