package com.fa20se28.vma.mapper;

import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.enums.UserDocumentType;
import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.UserDocumentDetail;
import com.fa20se28.vma.model.UserDocumentImage;
import com.fa20se28.vma.request.UserDocumentReq;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserDocumentMapper {
    @Select({"<script>" +
            "SELECT " +
            "ud.user_document_id," +
            "ud.user_document_number, " +
            "ud.user_document_type, " +
            "ud.registered_location, " +
            "ud.registered_date, " +
            "ud.expiry_date, " +
            "ud.other_information " +
            "FROM user_document ud " +
            "WHERE ud.user_id = #{user_id} " +
            "<if test = \"documentStatus!=null\" >\n" +
            "AND ud.status = #{documentStatus} \n" +
            "</if> \n" +
            "</script>"})
    @Results(id = "userDocumentResult", value = {
            @Result(property = "userDocumentId", column = "user_document_id"),
            @Result(property = "userDocumentNumber", column = "user_document_number"),
            @Result(property = "userDocumentType", column = "user_document_type"),
            @Result(property = "registeredLocation", column = "registered_location"),
            @Result(property = "registeredDate", column = "registered_date"),
            @Result(property = "expiryDate", column = "expiry_date"),
            @Result(property = "otherInformation", column = "other_information"),
            @Result(property = "userDocumentImages", column = "user_document_id",
                    many = @Many(select = "findDocumentImagesByUserDocumentId")),
    })
    List<UserDocument> findUserDocumentByUserId(@Param("user_id") String userId, @Param("documentStatus") DocumentStatus documentStatus);

    @Select("SELECT " +
            "di.user_document_image_id, " +
            "di.image_link " +
            "FROM user_document_image di " +
            "WHERE di.user_document_id = '${user_document_id}' ")
    @Results(id = "documentImageResult", value = {
            @Result(property = "userDocumentImageId", column = "user_document_image_id"),
            @Result(property = "imageLink", column = "image_link")
    })
    List<UserDocumentImage> findDocumentImagesByUserDocumentId(
            @Param("user_document_id") String userDocumentId);

    @Insert("INSERT INTO user_document " +
            "(user_document_number, " +
            "user_document_type, " +
            "user_id, " +
            "registered_location, " +
            "registered_date, " +
            "expiry_date, " +
            "other_information," +
            "create_date," +
            "status) " +
            "VALUES  " +
            "(#{UserDocumentReq.userDocumentNumber}, " +
            "#{UserDocumentReq.userDocumentType}, " +
            "#{userId}, " +
            "#{UserDocumentReq.registeredLocation}, " +
            "#{UserDocumentReq.registeredDate}, " +
            "#{UserDocumentReq.expiryDate}, " +
            "#{UserDocumentReq.otherInformation}," +
            "getDate()," +
            "#{status}) ")
    @Options(keyProperty = "UserDocumentReq.userDocumentId", useGeneratedKeys = true)
    int insertDocument(@Param("UserDocumentReq") UserDocumentReq userDocumentReq,
                       @Param("userId") String userId,
                       @Param("status") DocumentStatus documentStatus);

    @Update("UPDATE user_document " +
            "SET " +
            "user_document_number = #{UserDocumentReq.userDocumentNumber}," +
            "registered_location = #{UserDocumentReq.registeredLocation}, " +
            "registered_date = CONVERT(date, #{UserDocumentReq.registeredDate}), " +
            "expiry_date = CONVERT(date, #{UserDocumentReq.expiryDate}), " +
            "other_information = #{UserDocumentReq.otherInformation} " +
            "WHERE user_document_id = #{userDocumentId} " +
            "AND user_id = #{userId}")
    int updateDocument(@Param("UserDocumentReq") UserDocumentReq userDocumentReq,
                       @Param("userDocumentId") int userDocumentId,
                       @Param("userId") String userId);

    @Update("UPDATE user_document \n" +
            "SET status = 'DELETED' \n" +
            "WHERE user_document_id = '${user_document_id}'")
    void deleteUserDocument(@Param("user_document_id") String userDocumentId);

    @Select("SELECT \n" +
            "user_document_id, " +
            "user_document_number, \n" +
            "user_id, \n" +
            "registered_location, \n" +
            "registered_date, \n" +
            "expiry_date, \n" +
            "create_date, \n" +
            "other_information," +
            "status, \n" +
            "user_document_type\n" +
            "FROM user_document \n" +
            "WHERE user_document_number = #{userDocumentNumber} " +
            "AND status = #{status}")
    @Results(id = "userDocumentDetailResult", value = {
            @Result(property = "userDocumentId", column = "user_document_id"),
            @Result(property = "userDocumentNumber", column = "user_document_number"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "registeredLocation", column = "registered_location"),
            @Result(property = "registeredDate", column = "registered_date"),
            @Result(property = "expiryDate", column = "expiry_date"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "otherInformation", column = "other_information"),
            @Result(property = "documentStatus", column = "status"),
            @Result(property = "userDocumentType", column = "user_document_type"),
    })
    Optional<UserDocumentDetail> findUserDocumentDetail(@Param("userDocumentNumber") String userDocumentNumber,
                                                        @Param("status") DocumentStatus documentStatus);

    @Select("SELECT\n" +
            "user_document_type \n" +
            "FROM user_document \n" +
            "WHERE status = 'VALID' AND user_id = #{userId}")
    List<UserDocumentType> getCurrentUserDocumentTypes(@Param("userId") String userId);
}
