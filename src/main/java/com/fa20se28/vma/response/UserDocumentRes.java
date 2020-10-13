package com.fa20se28.vma.response;

import com.fa20se28.vma.model.UserDocument;

import java.util.List;

public class UserDocumentRes {
    private List<UserDocument> userDocuments;

    public UserDocumentRes(List<UserDocument> userDocuments) {
        this.userDocuments = userDocuments;
    }

    public List<UserDocument> getUserDocuments() {
        return userDocuments;
    }

    public void setUserDocuments(List<UserDocument> userDocuments) {
        this.userDocuments = userDocuments;
    }
}
