package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.UserDocumentComponent;
import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.response.UserDocumentDetailRes;
import com.fa20se28.vma.response.UserDocumentRes;
import com.fa20se28.vma.service.UserDocumentService;
import org.springframework.stereotype.Service;

@Service
public class UserDocumentServiceImpl implements UserDocumentService {
    private final UserDocumentComponent userDocumentComponent;

    public UserDocumentServiceImpl(UserDocumentComponent userDocumentComponent) {
        this.userDocumentComponent = userDocumentComponent;
    }

    @Override
    public UserDocumentRes getUserDocuments(String userId, DocumentStatus documentStatus) {
        return new UserDocumentRes(userDocumentComponent.findUserDocumentByUserId(userId, documentStatus));
    }

    @Override
    public void deleteUserDocument(String userDocumentId) {
        userDocumentComponent.deleteUserDocument(userDocumentId);
    }

    @Override
    public int createUserDocument(UserDocumentReq userDocumentReq, String userId) {
        return userDocumentComponent.createUserDocument(userDocumentReq, userId, DocumentStatus.VALID);
    }

    @Override
    public int updateUserDocument(UserDocumentReq userDocumentReq, String userId) {
        return userDocumentComponent.updateUserDocument(userDocumentReq, userId);
    }

    @Override
    public UserDocumentDetailRes getUserDocumentDetailById(String userDocumentId) {
        UserDocumentDetailRes userDocumentDetailRes = new UserDocumentDetailRes();
        userDocumentDetailRes.setUserDocumentDetail(userDocumentComponent.findUserDocumentDetailByNumber(userDocumentId, DocumentStatus.VALID));
        return userDocumentDetailRes;
    }
}
