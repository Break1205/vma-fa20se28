package com.fa20se28.vma.mapper;


import com.fa20se28.vma.enums.UserStatus;
import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.request.UserPageReq;
import com.fa20se28.vma.request.UserReq;
import com.fa20se28.vma.response.UserRes;
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
                            @Param("role_id") Long roleId);

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

    @Select({"<script>" +
            "SELECT\n" +
            "u.user_id, \n" +
            "u.full_name, \n" +
            "u.phone_number, \n" +
            "u.user_status\n" +
            "FROM\n" +
            "[user] u\n" +
            "JOIN user_roles ur\n" +
            "ON ur.user_id = u.user_id\n" +
            "WHERE ur.role_id = #{roleId} \n" +
            "<if test = \"UserPageReq.userId!=null\" >\n" +
            "AND u.user_id LIKE '%${UserPageReq.userId}%'\n" +
            "</if> \n" +
            "<if test = \"UserPageReq.fullName!=null\" >\n" +
            "AND u.full_name LIKE N'%${UserPageReq.fullName}%'\n" +
            "</if> \n" +
            "<if test = \"UserPageReq.phoneNumber!=null\" >\n" +
            "AND u.phone_number LIKE '%${UserPageReq.phoneNumber}%'\n" +
            "</if> \n" +
            "<if test = \"UserPageReq.userStatus!=null\" >\n" +
            "AND u.user_status = #{UserPageReq.userStatus}\n" +
            "</if> \n" +
            "AND u.user_id NOT IN \n" +
            "(SELECT user_id \n" +
            "FROM user_roles \n" +
            "WHERE role_id = \n" +
            "<if test = \"roleId == 2\" >\n" +
            "3\n" +
            "</if> \n" +
            "<if test = \"roleId == 3\" >\n" +
            "2\n" +
            "</if> \n" +
            ")\n" +
            "ORDER BY u.user_id ASC\n" +
            "OFFSET ${UserPageReq.page} ROWS\n" +
            "FETCH NEXT 15 ROWS ONLY " +
            "</script>"})
    @Results(id = "usersWithOnlyOneRoleResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "userStatus", column = "user_status")
    })
    List<UserRes> findUsersWithOneRoleByRoleId(@Param("roleId") String roleId, @Param("UserPageReq") UserPageReq userPageReq);

    @Select({"<script>" +
            "SELECT COUNT(uur.user_id) " +
            "FROM (" +
            "SELECT\n" +
            "u.user_id, \n" +
            "u.full_name, \n" +
            "u.phone_number, \n" +
            "u.user_status\n" +
            "FROM\n" +
            "[user] u\n" +
            "JOIN user_roles ur\n" +
            "ON ur.user_id = u.user_id\n" +
            "WHERE ur.role_id = #{roleId} \n" +
            "<if test = \"UserPageReq.userId!=null\" >\n" +
            "AND u.user_id LIKE '%${UserPageReq.userId}%'\n" +
            "</if> \n" +
            "<if test = \"UserPageReq.fullName!=null\" >\n" +
            "AND u.full_name LIKE N'%${UserPageReq.fullName}%'\n" +
            "</if> \n" +
            "<if test = \"UserPageReq.phoneNumber!=null\" >\n" +
            "AND u.phone_number LIKE '%${UserPageReq.phoneNumber}%'\n" +
            "</if> \n" +
            "<if test = \"UserPageReq.userStatus!=null\" >\n" +
            "AND u.user_status = #{UserPageReq.userStatus}\n" +
            "</if> \n" +
            "AND u.user_id NOT IN \n" +
            "(SELECT user_id \n" +
            "FROM user_roles \n" +
            "WHERE role_id = \n" +
            "<if test = \"roleId == 2\" >\n" +
            "3\n" +
            "</if> \n" +
            "<if test = \"roleId == 3\" >\n" +
            "2\n" +
            "</if> \n" +
            ")) uur \n" +
            "</script>"})
    int findTotalUsersWithOneRoleByRoleId(@Param("roleId") String roleId, @Param("UserPageReq") UserPageReq userPageReq);
}
