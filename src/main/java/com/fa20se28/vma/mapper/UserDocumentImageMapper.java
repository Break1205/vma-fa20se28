package com.fa20se28.vma.mapper;

import com.fa20se28.vma.request.DocumentImageReq;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDocumentImageMapper {
    @Insert("INSERT INTO user_document_image " +
            "(user_document_id, " +
            "image_link, " +
            "create_date) " +
            "VALUES " +
            "(#{documentId}, " +
            "#{DocumentImageReq.imageLink}, " +
            "getdate())")
    int insertUserDocumentImage(@Param("DocumentImageReq") DocumentImageReq documentImageReq,
                                @Param("documentId") String documentId);

    @Update("UPDATE document_image " +
            "SET " +
            "image_link = #{DocumentImageReq.imageLink}, " +
            "create_date = getdate() " +
            "WHERE document_id = #{documentId} " +
            "AND document_image_id = #{DocumentImageReq.documentImageId} ")
    int updateUserDocumentImage(@Param("DocumentImageReq") DocumentImageReq documentImageReq,
                                @Param("documentId") String documentId);
}
