package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.request.VehicleDocumentStandaloneReq;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;
import com.fa20se28.vma.response.UserDocumentRes;
import com.fa20se28.vma.response.UserDocumentTypesRes;
import com.fa20se28.vma.response.VehicleDocumentRes;
import com.fa20se28.vma.response.VehicleDocumentTypeRes;
import com.fa20se28.vma.service.DocumentService;
import com.fa20se28.vma.service.VehicleDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class DocumentController {
    private final DocumentService documentService;
    private final VehicleDocumentService vehicleDocumentService;

    public DocumentController(
            DocumentService documentService,
            VehicleDocumentService vehicleDocumentService) {
        this.documentService = documentService;
        this.vehicleDocumentService = vehicleDocumentService;
    }

    @GetMapping("/users/{user-id}/user-documents")
    public UserDocumentRes getUserDocuments(@PathVariable("user-id") String userId,
                                            @RequestParam(defaultValue = "0") int option) {
        return documentService.getUserDocuments(userId, option);
    }

    @GetMapping("/documents/types")
    public UserDocumentTypesRes getUserDocumentTypes() {
        return new UserDocumentTypesRes();
    }

    @PostMapping("/admin/user/{user-id}/documents")
    public int createUserDocument(@RequestBody UserDocumentReq userDocumentReq,
                                  @PathVariable("user-id") String userId) {
        return documentService.createUserDocument(userDocumentReq, userId);
    }

    @PutMapping("/admin/user/{user-id}/documents")
    public int updateUserDocument(@RequestBody UserDocumentReq userDocumentReq,
                                  @PathVariable("user-id") String userId) {
        return documentService.updateUserDocument(userDocumentReq, userId);
    }

    @DeleteMapping("/admin/documents")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserDocument(@RequestParam String userDocumentId) {
        documentService.deleteUserDocument(userDocumentId);
    }

    @GetMapping("/vehicles/documents")
    public VehicleDocumentRes getVehicleDocuments(
            @RequestParam String vehicleId,
            @RequestParam(defaultValue = "1") int viewOption) {
        return vehicleDocumentService.getVehicleDocuments(vehicleId, viewOption);
    }

    @PostMapping("/vehicles/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public void createVehicleDocument(
            @RequestBody VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq) {
        vehicleDocumentService.createVehicleDocument(vehicleDocumentStandaloneReq);
    }

    @PatchMapping("/vehicles/documents")
    public void updateVehicleDocument(@RequestBody VehicleDocumentUpdateReq vehicleDocumentUpdateReq) {
        vehicleDocumentService.updateVehicleDocument(vehicleDocumentUpdateReq);
    }

    @DeleteMapping("/vehicles/documents")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicleDocument(@RequestParam String vehicleDocId) {
        vehicleDocumentService.deleteDocument(vehicleDocId);
    }

    @GetMapping("/vehicles/documents/types")
    public VehicleDocumentTypeRes getVehicleDocumentTypes() {
        return new VehicleDocumentTypeRes();
    }
}
