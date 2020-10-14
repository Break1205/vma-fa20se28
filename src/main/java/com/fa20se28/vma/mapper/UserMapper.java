package com.fa20se28.vma.mapper;


import com.fa20se28.vma.request.DocumentImageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserDocumentReq;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
