package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Brand;
import com.fa20se28.vma.request.BrandReq;
import com.fa20se28.vma.request.BrandUpdateReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BrandMapper {

    @Select("SELECT b.brand_id, b.brand_name " +
            "FROM brand b ")
    @Results(id = "types", value = {
            @Result(property = "brandId", column = "brand_id"),
            @Result(property = "brandName", column = "brand_name"),
    })
    List<Brand> getBrands();

    @Insert("INSERT INTO brand " +
            "(brand_name) " +
            "VALUES " +
            "(#{b_request.brandName}) ")
    int createBrand(@Param("b_request") BrandReq brandReq);

    @Update("UPDATE brand " +
            "SET " +
            "brand_name = #{b_request.brandName} " +
            "WHERE brand_id = #{b_request.brandId} ")
    int updateBrand(@Param("b_request") BrandUpdateReq brandUpdateReq);
}
