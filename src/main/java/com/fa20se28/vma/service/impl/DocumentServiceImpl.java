package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.DocumentComponent;
import com.fa20se28.vma.response.UserDocumentRes;
import com.fa20se28.vma.response.UserDocumentTypesRes;
import com.fa20se28.vma.service.DocumentService;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentComponent documentComponent;

    public DocumentServiceImpl(DocumentComponent documentComponent) {
        this.documentComponent = documentComponent;
    }

    @Override
    public UserDocumentRes getUserDocuments(String id) {
        return new UserDocumentRes(documentComponent.findUserDocumentByUserId(id));
    }
}
