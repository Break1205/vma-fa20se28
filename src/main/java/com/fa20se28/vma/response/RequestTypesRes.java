package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.RequestType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestTypesRes {
    public List<RequestType> getRequestTypes() {
        return Stream.of(RequestType.values()).collect(Collectors.toList());
    }
}
