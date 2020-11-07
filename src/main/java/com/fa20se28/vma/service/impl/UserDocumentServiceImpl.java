package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.DocumentComponent;
import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.response.UserDocumentRes;
import com.fa20se28.vma.service.DocumentService;
import org.springframework.stereotype.Service;

@Service
public class UserDocumentServiceImpl implements DocumentService {
    private final DocumentComponent documentComponent;

    public UserDocumentServiceImpl(DocumentComponent documentComponent) {
        this.documentComponent = documentComponent;
    }

    @Override
    public UserDocumentRes getUserDocuments(String id, int option) {
        return new UserDocumentRes(documentComponent.findUserDocumentByUserId(id, option));
    }

    @Override
    public void deleteUserDocument(String userDocumentId) {
        documentComponent.deleteUserDocument(userDocumentId);
    }

    @Override
    public int createUserDocument(UserDocumentReq userDocumentReq, String userId) {
        return documentComponent.createUserDocument(userDocumentReq, userId);
    }

    @Override
    public int updateUserDocument(UserDocumentReq userDocumentReq, String userId) {
        return documentComponent.updateUserDocument(userDocumentReq, userId);
    }

    @Override
    public int createUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId) {
        return documentComponent.createUserDocumentWithRequest(userDocumentReq, userId);
    }

    @Override
    public int updateUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId) {
        return documentComponent.updateUserDocumentWithRequest(userDocumentReq, userId);
    }
}
