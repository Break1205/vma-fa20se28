package com.fa20se28.vma.mapper;


import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
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
            "SET user_status_id = #{user_status_id} " +
            "WHERE user_id = '${user_id}'")
    int updateUserStatusByUserId(@Param("user_status_id") Long userStatusId,
                                 @Param("user_id") String userId);

    @Select("SELECT " +
            "u.user_id, " +
            "u.user_status_id, " +
            "u.full_name, " +
            "u.phone_number, " +
            "u.image_link " +
            "FROM [user] u  " +
            "WHERE u.user_id = #{user_id}")
    @Results(id = "userAccountResult", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userStatusId", column = "user_status_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "imageLink", column = "image_link"),
    })
    Optional<User> findUserByUserId(@Param("user_id") String userId);

    @Select("SELECT " +
            "r.role_id " +
            "r.role_name " +
            "FROM user_roles ur " +
            "JOIN role r " +
            "ON ur.role_id = r.role_id " +
            "WHERE ur.user_id = '${user_id}'")
    List<Role> findUserRoles(@Param("user_id") String userId);

}
