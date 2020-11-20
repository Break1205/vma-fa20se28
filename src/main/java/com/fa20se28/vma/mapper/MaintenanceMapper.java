package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Maintenance;
import com.fa20se28.vma.model.MaintenanceDetail;
import com.fa20se28.vma.request.MaintenancePageReq;
import com.fa20se28.vma.request.MaintenanceReq;
import com.fa20se28.vma.request.MaintenanceUpdateReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MaintenanceMapper {
    @Insert("INSERT INTO maintenance " +
            "(vehicle_id, " +
            "start_date," +
            "end_date, " +
            "maintenance_type, " +
            "cost, " +
            "image_link, " +
            "description, " +
            "create_date, " +
            "is_deleted) " +
            "VALUES " +
            "(#{m_req.vehicleId}, " +
            "#{m_req.startDate}, " +
            "#{m_req.endDate}, " +
            "#{m_req.maintenanceType}, " +
            "#{m_req.cost}, " +
            "#{m_req.imageLink}, " +
            "#{m_req.description}, " +
            "getDate(), " +
            "0) ")
    int createMaintenance(@Param("m_req") MaintenanceReq maintenanceReq);

    @Update("UPDATE maintenance " +
            "SET " +
            "start_date = #{mu_request.startDate}, " +
            "end_date = #{mu_request.endDate}, " +
            "maintenance_type = #{mu_request.maintenanceType}, " +
            "cost = #{mu_request.cost}, " +
            "image_link = #{mu_request.imageLink}, " +
            "description = #{mu_request.description} " +
            "WHERE maintenance_id = #{mu_request.maintenanceId} ")
    int updateMaintenance(@Param("mu_request") MaintenanceUpdateReq maintenanceUpdateReq);

    @Update("UPDATE maintenance " +
            "SET " +
            "is_deleted = '1' " +
            "WHERE " +
            "maintenance_id = #{m_id} ")
    int deleteMaintenance(@Param("m_id") int maintenanceId);

    @Select({"<script> " +
            "SELECT COUNT(m.maintenance_id) "  +
            "FROM maintenance m " +
            "WHERE " +
            "<if test = \"m_option == 0\" > " +
            "m.is_deleted != 1 " +
            "</if> " +
            "<if test = \"m_option == 1\" > " +
            "m.is_deleted = 1 " +
            "</if> " +
            "<if test = \"m_req.vehicleId != null\" > " +
            "AND m.vehicle_id LIKE '%${m_req.vehicleId}%' " +
            "</if> " +
            "<if test = \"m_req.startDate != null \" > " +
            "AND m.start_date &gt;= #{m_req.startDate} " +
            "</if> " +
            "<if test = \"m_req.endDate != null \" > " +
            "AND m.end_date &lt;= #{m_req.endDate} " +
            "</if> " +
            "<if test = \"m_req.maintenanceType != null \" > " +
            "AND m.maintenance_type = #{m_req.maintenanceType} " +
            "</if> " +
            "<if test = \"m_req.costMin != 0 \" > " +
            "AND m.cost &gt;= #{m_req.costMin} " +
            "</if> " +
            "<if test = \"m_req.costMax != 0 \" > " +
            "AND m.cost &lt;= #{m_req.costMax} " +
            "</if> " +
            "</script>"})
    int getMaintenanceCount(
            @Param("m_req") MaintenancePageReq maintenancePageReq,
            @Param("m_option") int viewOption);

    @Select({"<script> " +
            "SELECT m.maintenance_id, m.vehicle_id, m.start_date, m.end_date, m.maintenance_type, m.cost "  +
            "FROM maintenance m " +
            "WHERE " +
            "<if test = \"m_option == 0\" > " +
            "m.is_deleted != 1 " +
            "</if> " +
            "<if test = \"m_option == 1\" > " +
            "m.is_deleted = 1 " +
            "</if> " +
            "<if test = \"m_req.vehicleId != null\" > " +
            "AND m.vehicle_id LIKE '%${m_req.vehicleId}%' " +
            "</if> " +
            "<if test = \"m_req.startDate != null \" > " +
            "AND m.start_date &gt;= #{m_req.startDate} " +
            "</if> " +
            "<if test = \"m_req.endDate != null \" > " +
            "AND m.end_date &lt;= #{m_req.endDate} " +
            "</if> " +
            "<if test = \"m_req.maintenanceType != null \" > " +
            "AND m.maintenance_type = #{m_req.maintenanceType} " +
            "</if> " +
            "<if test = \"m_req.costMin != 0 \" > " +
            "AND m.cost &gt;= #{m_req.costMin} " +
            "</if> " +
            "<if test = \"m_req.costMax != 0 \" > " +
            "AND m.cost &lt;= #{m_req.costMax} " +
            "</if> " +
            "ORDER BY m.create_date DESC " +
            "OFFSET ${m_offset} ROWS " +
            "</script>"})
    @Results(id = "maintenanceList", value = {
            @Result(property = "maintenanceId", column = "maintenance_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "maintenanceType", column = "maintenance_type"),
    })
    List<Maintenance> getMaintenances(
            @Param("m_req") MaintenancePageReq maintenancePageReq,
            @Param("m_option") int viewOption,
            @Param("m_offset") int offset);

    @Select("SELECT m.maintenance_id, m.vehicle_id, " +
            "m.start_date, m.end_date, " +
            "m.maintenance_type, m.cost, m.image_link, m.description "  +
            "FROM maintenance m " +
            "WHERE maintenance_id = #{m_id} ")
    @Results(id = "maintenanceDetail", value = {
            @Result(property = "maintenanceId", column = "maintenance_id"),
            @Result(property = "vehicleId", column = "vehicle_id"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "maintenanceType", column = "maintenance_type"),
            @Result(property = "imageLink", column = "image_link")
    })
    MaintenanceDetail getMaintenanceDetail(@Param("m_id") int maintenanceId);
}
