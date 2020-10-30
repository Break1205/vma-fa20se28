package com.fa20se28.vma.service;

import com.fa20se28.vma.response.UserDocumentRes;
import com.fa20se28.vma.response.UserDocumentTypesRes;

public interface DocumentService {
    UserDocumentRes getUserDocuments(String id);
}
