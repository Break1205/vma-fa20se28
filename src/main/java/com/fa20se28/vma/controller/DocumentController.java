package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.response.UserDocumentRes;
import com.fa20se28.vma.response.UserDocumentTypesRes;
import com.fa20se28.vma.service.DocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/documents/users")
    public UserDocumentRes getUserDocuments(@RequestParam(required = false) String userId) {
        return documentService.getUserDocuments(userId);
    }

    @PostMapping("/admin/documents/users")
    public int createUserDocument(@RequestBody UserDocumentReq userDocumentReq,
                                  @RequestParam String userId) {
        return documentService.createUserDocument(userDocumentReq, userId);
    }

    @PostMapping("/documents/users")
    public int createUserDocumentWithRequest(@RequestBody UserDocumentReq userDocumentReq,
                                             @RequestParam String userId) {
        return documentService.createUserDocumentWithRequest(userDocumentReq, userId);
    }

    @PutMapping("/admin/documents/users")
    public int updateUserDocument(@RequestBody UserDocumentReq userDocumentReq,
                                  @RequestParam String userId) {
        return documentService.updateUserDocument(userDocumentReq, userId);
    }

    @PutMapping("/documents/users")
    public int updateUserDocumentWithRequest(@RequestBody UserDocumentReq userDocumentReq,
                                             @RequestParam String userId) {
        return documentService.updateUserDocumentWithRequest(userDocumentReq, userId);
    }

    @GetMapping("/documents/types")
    public UserDocumentTypesRes getUserDocumentTypes() {
        return new UserDocumentTypesRes();
    }
}
