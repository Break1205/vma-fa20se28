package com.fa20se28.vma.configuration;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CustomUtils {

    // Generate 8 characters random ID
    public static String randomId(){
        return RandomStringUtils.randomAlphanumeric(8);
    }

    // Copy values of two objects with similar properties that have value
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    // Get name of properties that has null values
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    // Compare if date1 <= date2
    public static boolean isBeforeOrEqualDate(LocalDate date, LocalDate dateCompare) {
        if (date != null && dateCompare !=null) {
            return !date.isAfter(dateCompare);
        }

        return false;
    }

    // Compare if date1 >= date2
    public static boolean isAfterOrEqualDate(LocalDate date, LocalDate dateCompare) {
        if (date != null && dateCompare !=null) {
            return !date.isBefore(dateCompare);
        }

        return false;
    }
}
