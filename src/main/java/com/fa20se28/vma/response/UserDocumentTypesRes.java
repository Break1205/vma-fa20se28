package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.UserDocumentType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDocumentTypesRes {
    public List<UserDocumentType> getUserDocumentTypes() {
        return Stream.of(UserDocumentType.values()).collect(Collectors.toList());
    }
}
