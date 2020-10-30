package com.fa20se28.vma.service;

import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.response.UserDocumentRes;

public interface DocumentService {
    UserDocumentRes getUserDocuments(String id);

    int createUserDocument(UserDocumentReq userDocumentReq, String userId);

    int updateUserDocument(UserDocumentReq userDocumentReq, String userId);

    int createUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId);

    int updateUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId);
}
