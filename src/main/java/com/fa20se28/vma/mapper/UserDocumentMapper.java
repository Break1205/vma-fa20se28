package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.DocumentImage;
import com.fa20se28.vma.request.UserDocumentReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDocumentMapper {
    @Select("SELECT " +
            "ud.user_document_id, " +
            "udt.user_document_type_id, " +
            "udt.user_document_type_name, " +
            "ud.registered_location, " +
            "ud.registered_date, " +
            "ud.expiry_date, " +
            "ud.other_information " +
            "FROM user_document ud " +
            "JOIN user_document_type udt " +
            "ON ud.user_document_type_id = udt.user_document_type_id " +
            "WHERE ud.user_id = '${user_id}' ")
    @Results(id = "userDocumentResult", value = {
            @Result(property = "userDocumentId", column = "user_document_id"),
            @Result(property = "userDocumentType.userDocumentTypeId", column = "user_document_type_id"),
            @Result(property = "userDocumentType.userDocumentTypeName", column = "user_document_type_name"),
            @Result(property = "registerLocation", column = "registered_location"),
            @Result(property = "registerDate", column = "registered_date"),
            @Result(property = "expiryDate", column = "expiry_date"),
            @Result(property = "otherInformation", column = "other_information"),
            @Result(property = "documentImages", column = "user_document_id",
                    many = @Many(select = "findDocumentImagesByUserDocumentId")),
    })
    List<UserDocument> findUserDocumentByUserId(@Param("user_id") String userId);

    @Select("SELECT " +
            "di.document_image_id, " +
            "di.image_link " +
            "FROM document_image di " +
            "WHERE di.document_id = '${document_id}' ")
    @Results(id = "documentImageResult", value = {
            @Result(property = "documentImageId", column = "document_image_id"),
            @Result(property = "imageLink", column = "image_link")
    })
    List<DocumentImage> findDocumentImagesByUserDocumentId(
            @Param("document_id") String documentId);

    @Insert("INSERT INTO user_document " +
            "(user_document_id, " +
            "user_document_type_id, " +
            "user_id, " +
            "registered_location, " +
            "registered_date, " +
            "expiry_date, " +
            "other_information) " +
            "VALUES  " +
            "(#{UserDocumentReq.userDocumentId}, " +
            "#{UserDocumentReq.userDocumentTypeId}, " +
            "#{userId}, " +
            "#{UserDocumentReq.registeredLocation}, " +
            "#{UserDocumentReq.registeredDate}, " +
            "#{UserDocumentReq.expiryDate}, " +
            "#{UserDocumentReq.otherInformation}) ")
    int insertDocument(@Param("UserDocumentReq") UserDocumentReq userDocumentReq,
                       @Param("userId") String userId);

    @Update("UPDATE user_document " +
            "SET " +
            "user_document_type_id = #{UserDocumentReq.userDocumentTypeId}, " +
            "registered_location = #{UserDocumentReq.registeredLocation}, " +
            "registered_date = CONVERT(date, #{UserDocumentReq.registeredDate}), " +
            "expiry_date = CONVERT(date, #{UserDocumentReq.expiryDate}), " +
            "other_information = #{UserDocumentReq.otherInformation} " +
            "WHERE user_document_id = #{UserDocumentReq.userDocumentId} " +
            "AND user_id = #{userId}")
    void updateDocument(@Param("UserDocumentReq") UserDocumentReq userDocumentReq,
                        @Param("userId") String userId);
}
