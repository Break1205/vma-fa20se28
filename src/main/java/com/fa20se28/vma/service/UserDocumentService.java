package com.fa20se28.vma.service;

import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.response.UserDocumentDetailRes;
import com.fa20se28.vma.response.UserDocumentRes;

public interface UserDocumentService {
    UserDocumentRes getUserDocuments(String id, DocumentStatus documentStatus);

    int createUserDocument(UserDocumentReq userDocumentReq, String userId);

    int updateUserDocument(UserDocumentReq userDocumentReq, String userId);

    void deleteUserDocument(int userDocumentId);

    UserDocumentDetailRes getUserDocumentDetailById(int userDocumentId);
}
