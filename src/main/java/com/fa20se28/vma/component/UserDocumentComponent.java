package com.fa20se28.vma.component;

import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.UserDocumentDetail;
import com.fa20se28.vma.request.UserDocumentReq;

import java.util.List;

public interface UserDocumentComponent {
    List<UserDocument> findUserDocumentByUserId(String id, int option);

    int createUserDocument(UserDocumentReq userDocumentReq, String userId);

    int updateUserDocument(UserDocumentReq userDocumentReq, String userId);

    int createUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId);

    int updateUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId);

    void deleteUserDocument(String userDocumentId);

    int acceptNewDocumentRequest(String userDocumentId);

    int denyUpdateDocumentRequest(String userDocumentId);

    UserDocumentDetail findUserDocumentDetailById(String userDocumentId);
}
