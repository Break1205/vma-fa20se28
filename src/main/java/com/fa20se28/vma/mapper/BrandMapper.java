package com.fa20se28.vma.mapper;

import com.fa20se28.vma.model.Brand;
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
}
