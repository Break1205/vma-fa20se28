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
        if (id == null)
        {
            return new UserDocumentRes(documentComponent.getUserDocuments());
        }
        else
        {
            return new UserDocumentRes(documentComponent.findUserDocumentById(id));
        }
    }

    @Override
    public UserDocumentTypesRes getUserDocumentTypes() {
        return new UserDocumentTypesRes(documentComponent.getUserDocumentTypes());
    }
}
