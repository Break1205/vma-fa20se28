package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.DocumentImage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDocumentMapper {
    @Select("SELECT " +
            "ud.user_document_id, " +
            "udt.user_document_type_name, " +
            "ud.user_id, " +
            "ud.registered_location, " +
            "ud.registered_date, " +
            "ud.expiry_date " +
            "FROM user_document ud " +
            "JOIN user_document_type udt " +
            "ON ud.user_document_type_id = udt.user_document_type_id " +
            "WHERE ud.user_id = ${user_id}")
    @Results(id = "userDocumentResult", value = {
            @Result(property = "userDocumentId", column = "user_document_id"),
            @Result(property = "userDocumentType", column = "user_document_type_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "registerLocation", column = "registered_location"),
            @Result(property = "registerDate", column = "registered_date"),
            @Result(property = "expiryDate", column = "expiry_date"),
            @Result(property = "documentImages", column = "user_document_id",
                    many = @Many(select = "findDocumentImagesByUserDocumentId")),
    })
    List<UserDocument> findUserDocumentByUserId(@Param("user_id") String userId);

    @ResultMap("userDocumentResult")
    @Select("SELECT " +
            "ud.user_document_id, " +
            "udt.user_document_type_name, " +
            "ud.user_id, " +
            "ud.registered_location, " +
            "ud.registered_date, " +
            "ud.expiry_date " +
            "FROM user_document ud " +
            "JOIN user_document_type udt " +
            "ON ud.user_document_type_id = udt.user_document_type_id ")
    List<UserDocument> getUserDocuments();

    @Select("SELECT " +
            "di.document_image_id, " +
            "di.image_link " +
            "FROM document_image di " +
            "WHERE di.document_id = '${document_id}' ")
    @Results(id = "documentImageResult",value = {
            @Result(property = "documentImageId", column = "document_image_id"),
            @Result(property = "imageLink", column = "image_link")
    })
    List<DocumentImage> findDocumentImagesByUserDocumentId(
            @Param("document_id") String documentId);
}
