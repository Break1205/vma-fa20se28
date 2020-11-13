package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Brand;

import java.util.List;

public class BrandRes {
    private List<Brand> brands;

    public BrandRes(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
