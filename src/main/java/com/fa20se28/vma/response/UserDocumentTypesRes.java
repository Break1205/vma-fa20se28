package com.fa20se28.vma.response;

import com.fa20se28.vma.model.UserDocumentType;

import java.util.List;

public class UserDocumentTypesRes {
    private List<UserDocumentType> userDocumentTypes;

    public UserDocumentTypesRes(List<UserDocumentType> userDocumentTypes) {
        this.userDocumentTypes = userDocumentTypes;
    }

    public List<UserDocumentType> getUserDocumentTypes() {
        return userDocumentTypes;
    }

    public void setUserDocumentTypes(List<UserDocumentType> userDocumentTypes) {
        this.userDocumentTypes = userDocumentTypes;
    }
}
