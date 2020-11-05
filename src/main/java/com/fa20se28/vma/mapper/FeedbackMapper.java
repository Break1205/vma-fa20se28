package com.fa20se28.vma.mapper;

import com.fa20se28.vma.request.FeedbackReq;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface FeedbackMapper {
    @Insert("INSERT INTO feedback " +
            "(" +
            "issued_vehicle_id, " +
            "owner_id, " +
            "rate, " +
            "comment, " +
            "create_date, " +
            "is_deleted) " +
            "VALUES " +
            "(" +
            "#{FeedbackReq.issuedVehicleId}, " +
            "#{ownerId}, " +
            "#{FeedbackReq.rate}, " +
            "#{FeedbackReq.comment}, " +
            "getDate(), " +
            "0)")
    int insertFeedback(@Param("FeedbackReq") FeedbackReq feedbackReq, @Param("ownerId") String ownerId);

    @Update("UPDATE feedback " +
            "SET " +
            "rate = #{FeedbackReq.rate}, " +
            "comment = #{FeedbackReq.comment} " +
            "WHERE issued_vehicle_id = #{FeedbackReq.issuedVehicleId}")
    void updateFeedback(@Param("FeedbackReq") FeedbackReq feedbackReq);

    @Update("UPDATE feedback " +
            "SET " +
            "is_deleted = 1" +
            "WHERE feedback_id = #{issuedVehicleId}")
    void deleteFeedback(@Param("issuedVehicleId") int issuedVehicleId);

}
