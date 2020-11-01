package com.fa20se28.vma.mapper;

import com.fa20se28.vma.request.UserDocumentImageReq;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDocumentImageMapper {
    @Insert("INSERT INTO user_document_image " +
            "(user_document_id, " +
            "image_link, " +
            "create_date," +
            "is_deleted) " +
            "VALUES " +
            "(#{documentId}, " +
            "#{UserDocumentImageReq.imageLink}, " +
            "getdate()," +
            "0)")
    @Options(keyProperty = "UserDocumentImageReq.userDocumentImageId", useGeneratedKeys = true)
    int insertUserDocumentImage(@Param("UserDocumentImageReq") UserDocumentImageReq userDocumentImageReq,
                                @Param("documentId") String documentId);

    @Update("UPDATE user_document_image " +
            "SET " +
            "image_link = #{UserDocumentImageReq.imageLink}, " +
            "create_date = getdate() " +
            "WHERE user_document_id = #{documentId} " +
            "AND user_document_image_id = #{UserDocumentImageReq.userDocumentImageId} ")
    @Options(keyProperty = "UserDocumentImageReq.userDocumentImageId")
    int updateUserDocumentImage(@Param("UserDocumentImageReq") UserDocumentImageReq userDocumentImageReq,
                                @Param("documentId") String documentId);

    @Insert("INSERT INTO user_document_image_log " +
            "(user_document_image_id, " +
            "user_document_id, " +
            "image_link, " +
            "create_date)" +
            "VALUES " +
            "(#{UserDocumentImageReq.userDocumentImageId}, " +
            "#{documentId}, " +
            "#{UserDocumentImageReq.imageLink}, " +
            "getdate())")
    int insertUserDocumentImageLog(@Param("UserDocumentImageReq") UserDocumentImageReq userDocumentImageReq,
                                   @Param("documentId") String documentId);

    @Update("UPDATE user_document_image \n" +
            "SET is_deleted = 1 \n" +
            "WHERE user_document_id = '${user_document_id}'")
    void deleteUserDocumentImage(String userDocumentId);
}
