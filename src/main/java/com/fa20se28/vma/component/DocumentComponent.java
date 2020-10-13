package com.fa20se28.vma.component;

import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.UserDocumentType;

import java.util.List;

public interface DocumentComponent {
    List<UserDocument> findUserDocumentById(String id);

    List<UserDocument> getUserDocuments();

    List<UserDocumentType> getUserDocumentTypes();
}
