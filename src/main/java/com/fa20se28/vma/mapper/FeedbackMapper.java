package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Feedback;
import com.fa20se28.vma.request.FeedbackPageReq;
import com.fa20se28.vma.request.FeedbackReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    @Select({"<script>" +
            "SELECT COUNT(fb.feedback_id) " +
            "FROM feedback fb " +
            "JOIN issued_vehicle iv ON iv.issued_vehicle_id = fb.issued_vehicle_id " +
            "JOIN vehicle v ON v.vehicle_id = iv.vehicle_id " +
            "JOIN [user] ct ON ct.[user_id] = fb.owner_id  " +
            "JOIN [user] dr ON dr.[user_id] = iv.driver_id " +
            "WHERE " +
            "<if test = \"fb_option == 0\" > " +
            "fb.is_deleted = 0 " +
            "</if> " +
            "<if test = \"fb_option == 1\" > " +
            "fb.is_deleted = 1 " +
            "</if> " +
            "<if test = \"fb_req.vehicleId != null\" > " +
            "AND v.vehicle_id LIKE '%${fb_req.vehicleId}%' " +
            "</if> " +
            "<if test = \"fb_req.contributorId != null\" > " +
            "AND ct.[user_id] LIKE '%${fb_req.contributorId}%' " +
            "</if> " +
            "<if test = \"fb_req.contributorName != null\" > " +
            "AND ct.full_name LIKE '%${fb_req.contributorName}%' " +
            "</if> " +
            "<if test = \"fb_req.driverId != null\" > " +
            "AND dr.[user_id] LIKE '%${fb_req.driverId}%' " +
            "</if> " +
            "<if test = \"fb_req.driverName != null\" > " +
            "AND dr.full_name LIKE '%${fb_req.driverName}%' " +
            "</if> " +
            "<if test = \"fb_req.fromDate != null\" > " +
            "AND fb.create_date &gt;= #{fb_req.fromDate} " +
            "</if> " +
            "<if test = \"fb_req.toDate != null\" > " +
            "AND fb.create_date &lt;= #{fb_req.toDate} " +
            "</if> " +
            "<if test = \"fb_req.rateMin != 0\" > " +
            "AND fb.rate &gt;= #{fb_req.rateMin} " +
            "</if> " +
            "<if test = \"fb_req.rateMax != 0\" > " +
            "AND fb.rate &lt;= #{fb_req.rateMax} " +
            "</if> " +
            "</script> "})
    int getFeedbackCount(
            @Param("fb_req") FeedbackPageReq feedbackPageReq,
            @Param("fb_option") int viewOption);

    @Select({"<script>" +
            "SELECT fb.feedback_id, v.vehicle_id, " +
            "ct.[user_id] AS contributor_id, ct.full_name AS contributor_name, " +
            "dr.[user_id] AS driver_id, dr.full_name AS driver_name, " +
            "fb.create_date, fb.rate " +
            "FROM feedback fb " +
            "JOIN issued_vehicle iv ON iv.issued_vehicle_id = fb.issued_vehicle_id " +
            "JOIN vehicle v ON v.vehicle_id = iv.vehicle_id " +
            "JOIN [user] ct ON ct.[user_id] = fb.owner_id  " +
            "JOIN [user] dr ON dr.[user_id] = iv.driver_id " +
            "WHERE " +
            "<if test = \"fb_option == 0\" > " +
            "fb.is_deleted = 0 " +
            "</if> " +
            "<if test = \"fb_option == 1\" > " +
            "fb.is_deleted = 1 " +
            "</if> " +
            "<if test = \"fb_req.vehicleId != null\" > " +
            "AND v.vehicle_id LIKE '%${fb_req.vehicleId}%' " +
            "</if> " +
            "<if test = \"fb_req.contributorId != null\" > " +
            "AND ct.[user_id] LIKE '%${fb_req.contributorId}%' " +
            "</if> " +
            "<if test = \"fb_req.contributorName != null\" > " +
            "AND ct.full_name LIKE '%${fb_req.contributorName}%' " +
            "</if> " +
            "<if test = \"fb_req.driverId != null\" > " +
            "AND dr.[user_id] LIKE '%${fb_req.driverId}%' " +
            "</if> " +
            "<if test = \"fb_req.driverName != null\" > " +
            "AND dr.full_name LIKE '%${fb_req.driverName}%' " +
            "</if> " +
            "<if test = \"fb_req.fromDate != null\" > " +
            "AND fb.create_date &gt;= #{fb_req.fromDate} " +
            "</if> " +
            "<if test = \"fb_req.toDate != null\" > " +
            "AND fb.create_date &lt;= #{fb_req.toDate} " +
            "</if> " +
            "<if test = \"fb_req.rateMin != 0\" > " +
            "AND fb.rate &gt;= #{fb_req.rateMin} " +
            "</if> " +
            "<if test = \"fb_req.rateMax != 0\" > " +
            "AND fb.rate &lt;= #{fb_req.rateMax} " +
            "</if> " +
            "ORDER BY fb.create_date DESC " +
            "OFFSET ${fb_offset} ROWS " +
            "FETCH NEXT 15 ROWS ONLY " +
            "</script> "})
    @Results(id = "feedbackList", value = {
            @Result(property = "feedbackId", column = "feedback_id"),
            @Result(property = "contributor.userId", column = "contributor_id"),
            @Result(property = "contributor.userName", column = "contributor_name"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "driver.userId", column = "driver_id"),
            @Result(property = "driver.userName", column = "driver_name")
    })
    List<Feedback> getFeedbacks(
            @Param("fb_req") FeedbackPageReq feedbackPageReq,
            @Param("fb_option") int viewOption,
            @Param("fb_offset") int offset);
}
