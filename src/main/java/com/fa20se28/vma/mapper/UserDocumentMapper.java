package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.UserDocumentImage;
import com.fa20se28.vma.request.UserDocumentReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDocumentMapper {
    @Select("SELECT " +
            "ud.user_document_id, " +
            "ud.user_document_type, " +
            "ud.registered_location, " +
            "ud.registered_date, " +
            "ud.expiry_date, " +
            "ud.other_information " +
            "FROM user_document ud " +
            "WHERE ud.user_id = '${user_id}' " +
            "AND ud.is_deleted = 0")
    @Results(id = "userDocumentResult", value = {
            @Result(property = "userDocumentId", column = "user_document_id"),
            @Result(property = "userDocumentType", column = "user_document_type"),
            @Result(property = "registeredLocation", column = "registered_location"),
            @Result(property = "registeredDate", column = "registered_date"),
            @Result(property = "expiryDate", column = "expiry_date"),
            @Result(property = "otherInformation", column = "other_information"),
            @Result(property = "userDocumentImages", column = "user_document_id",
                    many = @Many(select = "findDocumentImagesByUserDocumentId")),
    })
    List<UserDocument> findUserDocumentByUserId(@Param("user_id") String userId);

    @Select("SELECT " +
            "di.user_document_image_id, " +
            "di.image_link " +
            "FROM user_document_image di " +
            "WHERE di.user_document_id = '${user_document_id}' " +
            "AND di.is_deleted = 0")
    @Results(id = "documentImageResult", value = {
            @Result(property = "userDocumentImageId", column = "user_document_image_id"),
            @Result(property = "imageLink", column = "image_link")
    })
    List<UserDocumentImage> findDocumentImagesByUserDocumentId(
            @Param("user_document_id") String userDocumentId);

    @Insert("INSERT INTO user_document " +
            "(user_document_id, " +
            "user_document_type, " +
            "user_id, " +
            "registered_location, " +
            "registered_date, " +
            "expiry_date, " +
            "other_information," +
            "create_date," +
            "is_deleted) " +
            "VALUES  " +
            "(#{UserDocumentReq.userDocumentId}, " +
            "#{UserDocumentReq.userDocumentType}, " +
            "#{userId}, " +
            "#{UserDocumentReq.registeredLocation}, " +
            "#{UserDocumentReq.registeredDate}, " +
            "#{UserDocumentReq.expiryDate}, " +
            "#{UserDocumentReq.otherInformation}," +
            "getDate()," +
            "0) ")
    @Options(keyProperty = "UserDocumentReq.userDocumentId", useGeneratedKeys = true)
    int insertDocument(@Param("UserDocumentReq") UserDocumentReq userDocumentReq,
                       @Param("userId") String userId);

    @Update("UPDATE user_document " +
            "SET " +
            "user_document_type = #{UserDocumentReq.userDocumentType}, " +
            "registered_location = #{UserDocumentReq.registeredLocation}, " +
            "registered_date = CONVERT(date, #{UserDocumentReq.registeredDate}), " +
            "expiry_date = CONVERT(date, #{UserDocumentReq.expiryDate}), " +
            "other_information = #{UserDocumentReq.otherInformation} " +
            "WHERE user_document_id = #{UserDocumentReq.userDocumentId} " +
            "AND user_id = #{userId}")
    int updateDocument(@Param("UserDocumentReq") UserDocumentReq userDocumentReq,
                       @Param("userId") String userId);

    @Insert("INSERT INTO user_document_log " +
            "(user_document_id, " +
            "user_document_type, " +
            "user_id, " +
            "registered_location, " +
            "registered_date, " +
            "expiry_date, " +
            "other_information," +
            "create_date) " +
            "VALUES  " +
            "(#{UserDocumentReq.userDocumentId}, " +
            "#{UserDocumentReq.userDocumentType}, " +
            "#{userId}, " +
            "#{UserDocumentReq.registeredLocation}, " +
            "#{UserDocumentReq.registeredDate}, " +
            "#{UserDocumentReq.expiryDate}, " +
            "#{UserDocumentReq.otherInformation}," +
            "getDate()) ")
    int insertDocumentLog(@Param("UserDocumentReq") UserDocumentReq userDocumentReq,
                          @Param("userId") String userId);

    @Update("UPDATE user_document \n" +
            "SET is_deleted = 1 \n" +
            "WHERE user_document_id = '${user_document_id}'")
    void deleteUserDocument(@Param("user_document_id") String userDocumentId);

}
