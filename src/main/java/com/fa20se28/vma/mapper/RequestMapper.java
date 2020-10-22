package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Request;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

@Mapper
public interface RequestMapper {

    @Insert("INSERT INTO request " +
            "(user_id, " +
            "request_status_id, " +
            "request_date, " +
            "description, " +
            "is_deleted) " +
            "VALUES " +
            "(#{userId}, " +
            "#{requestStatusId}, " +
            "getDate(), " +
            "#{description}, " +
            "#{isDeleted})")
    @Options(useGeneratedKeys = true, keyProperty = "requestId", keyColumn = "request_id")
    int insertRequest(Request request);


    @Select("SELECT " +
            "r.request_id " +
            "rs.request_status_id, " +
            "request_date " +
            "FROM request r " +
            "JOIN request_status rs " +
            "ON r.request_status_id = rs.request_status_id " +
            "JOIN user_request ur " +
            "ON r.request_id = ur.request_id " +
            "JOIN user_request_type urt " +
            "ON ur.user_request_type_id = urt.user_request_type_id " +
            "WHERE urt.user_request_type_id = #{userRequestTypeId} " +
            "AND user_id = #{userId}")
    @Results(id = "requestResult", value = {
            @Result(property = "requestId", column = "request_id"),
            @Result(property = "requestStatusId", column = "request_status_id"),
            @Result(property = "requestDate", column = "request_date")
    })
    Optional<Request> findRequestByUserId(@Param("userId") String userId,
                                          @Param("userRequestTypeId") Long userRequestTypeId);

    @Insert("INSERT INTO user_request " +
            "(request_id, " +
            "user_request_type_id) " +
            "VALUES " +
            "(#{requestId}, " +
            "#{user_request_type_id})")
    int insertUserRequest(@Param("requestId") Long requestId,
                          @Param("user_request_type_id") int userRequestTypeId);

    @Update("UPDATE request " +
            "SET request_status_id = #{requestStatusId}, " +
            "request_date = getDate() " +
            "WHERE request_id = #{requestId}")
    int updateRequestStatus(@Param("requestId") Long requestId,
                            @Param("requestStatusId") Long requestStatusId);
}
