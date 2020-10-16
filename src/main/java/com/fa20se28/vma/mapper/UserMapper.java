package com.fa20se28.vma.mapper;


import com.fa20se28.vma.model.Role;
import com.fa20se28.vma.model.User;
import com.fa20se28.vma.request.DocumentImageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserDocumentReq;
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

    @Insert("INSERT INTO [user] " +
            "(user_id, " +
            "user_status_id, " +
            "full_name, " +
            "phone_number, " +
            "gender, " +
            "date_of_birth, " +
            "address, " +
            "image_link, " +
            "base_salary) " +
            "VALUES " +
            "(#{userId}, " +
            "#{userStatusId}, " +
            "#{fullName}, " +
            "#{phoneNumber}, " +
            "#{gender}, " +
            "#{dateOfBirth}, " +
            "#{address}, " +
            "#{imageLink}, " +
            "#{baseSalary})")
    int insertDriver(DriverReq driverReq);

    @Insert("INSERT INTO user_roles " +
            "(user_id, " +
            "role_id) " +
            "VALUES " +
            "(#{user_id}, " +
            "#{role_id} " +
            ");")
    int insertRoleForUserId(@Param("user_id") String userId, @Param("role_id") int roleId);

    @Insert("INSERT INTO user_document " +
            "(user_document_id, " +
            "user_document_type_id, " +
            "user_id, " +
            "registered_location, " +
            "registered_date, " +
            "expiry_date, " +
            "other_information) " +
            "VALUES  " +
            "(#{userDocumentId}, " +
            "#{userDocumentTypeId}, " +
            "#{userId}, " +
            "#{registerLocation}, " +
            "#{registerDate}, " +
            "#{expiryDate}, " +
            "#{otherInformation}) ")
    int insertDocument(UserDocumentReq userDocumentReq);

    @Insert("INSERT INTO document_image " +
            "(document_id, " +
            "image_link, " +
            "create_date) " +
            "VALUES " +
            "(#{documentId}, " +
            "#{imageLink}, " +
            "getdate())")
    int insertDocumentImage(DocumentImageReq documentImageReq);

    @Update("UPDATE [user] " +
            "SET user_status_id = 4 " +
            "WHERE user_id = #{user_id} ")
    void deleteUserById(@Param("user_id") String userId);

    @Update("UPDATE [user] " +
            "SET " +
            "full_name = #{fullName}, " +
            "phone_number = #{phoneNumber}, " +
            "gender = #{gender}, " +
            "date_of_birth = ${dateOfBirth}, " +
            "address = ${address}, " +
            "image_link = ${imageLink}, " +
            "base_salary = ${baseSalary} " +
            "WHERE user_id = #{userId}")
    void updateDriver(DriverReq driverReq);

    @Update("UPDATE dbo.user_document " +
            "SET " +
            "user_document_type_id = #{userDocumentTypeId}, " +
            "registered_location = #{registeredLocation}, " +
            "registered_date = #{registeredDate}, " +
            "expiry_date = #{expiryDate}, " +
            "other_information = #{otherInformation} " +
            "WHERE user_document_id = #{userDocumentId}" +
            "AND user_id = #{userId}")
    void updateDocument(UserDocumentReq userDocumentReq);

    @Update("UPDATE dbo.document_image " +
            "SET " +
            "image_link = #{imageLink}, " +
            "create_date = getdate() " +
            "WHERE document_id = #{documentId} " +
            "AND doucment_image_id = #{documentImageId} ")
    void updateDocumentImage(DocumentImageReq documentImageReq);

    @Update("Update [user] " +
            "SET user_status_id = #{user_status_id} " +
            "WHERE user_id = #{user_id}")
    void updateUserStatusByUserId(@Param("user_status_id") Long userStatusId, @Param("user_id") String userId);

    @Select("SELECT u.user_id" +
            "FROM [user] u" +
            "WHERE u.user_id = #{user_id}")
    @Results(id = "userAccountResult", value = {
            @Result(property = "userId", column = "user_id")
    })
    Optional<User> findUserByUserId(@Param("user_id") String userId);

    @Select("SELECT " +
            "r.role_id " +
            "r.role_name " +
            "FROM user_roles ur " +
            "JOIN role r " +
            "ON ur.role_id = r.role_id " +
            "WHERE ur.user_id = #{user_id}")
    List<Role> findUserRoles(@Param("user_id") String userId);
}
