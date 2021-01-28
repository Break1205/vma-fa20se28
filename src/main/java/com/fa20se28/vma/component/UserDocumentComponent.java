package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.UserDocumentDetail;
import com.fa20se28.vma.request.UserDocumentReq;

import java.util.List;

public interface UserDocumentComponent {
    List<UserDocument> findUserDocumentByUserId(String id, DocumentStatus documentStatus);

    int createUserDocument(UserDocumentReq userDocumentReq, String userId, DocumentStatus documentStatus);

    int updateUserDocument(UserDocumentReq userDocumentReq, String userId);

    void deleteUserDocument(int userDocumentId);

    int createUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId, DocumentStatus documentStatus);

    int createUpdateUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId, DocumentStatus pending);

    void deleteUserDocumentWithRequest(String userDocumentId);

    int acceptNewDocumentRequest(String userDocumentId);

    int denyUpdateDocumentRequest(String userDocumentId);

    UserDocumentDetail findUserDocumentDetailById(int userDocumentId, DocumentStatus documentStatus);

}
