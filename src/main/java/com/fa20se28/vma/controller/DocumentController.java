package com.fa20se28.vma.controller;

import com.fa20se28.vma.response.UserDocumentRes;
import com.fa20se28.vma.response.UserDocumentTypesRes;
import com.fa20se28.vma.service.DocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/users")
    public UserDocumentRes getUserDocuments(@RequestParam(required = false) String userId) {
        return documentService.getUserDocuments(userId);
    }

    @GetMapping("/types")
    public UserDocumentTypesRes getUserDocumentTypes() {
        return new UserDocumentTypesRes();
    }
}
