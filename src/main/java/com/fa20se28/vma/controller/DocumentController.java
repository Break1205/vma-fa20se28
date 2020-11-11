package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.request.VehicleDocumentStandaloneReq;
import com.fa20se28.vma.request.VehicleDocumentUpdateReq;
import com.fa20se28.vma.response.*;
import com.fa20se28.vma.service.UserDocumentService;
import com.fa20se28.vma.service.VehicleDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class DocumentController {
    private final UserDocumentService userDocumentService;
    private final VehicleDocumentService vehicleDocumentService;

    public DocumentController(
            UserDocumentService userDocumentService,
            VehicleDocumentService vehicleDocumentService) {
        this.userDocumentService = userDocumentService;
        this.vehicleDocumentService = vehicleDocumentService;
    }

    @GetMapping("/users/{user-id}/user-documents")
    public UserDocumentRes getUserDocuments(@PathVariable("user-id") String userId,
                                            @RequestParam(defaultValue = "0") int option) {
        return userDocumentService.getUserDocuments(userId, option);
    }

    @GetMapping("/user-documents/{user-document-id}")
    public UserDocumentDetailRes getUserDocumentDetail(@PathVariable("user-document-id") String userDocumentId) {
        return userDocumentService.getUserDocumentDetailById(userDocumentId);
    }

    @GetMapping("/user-documents/types")
    public UserDocumentTypesRes getUserDocumentTypes() {
        return new UserDocumentTypesRes();
    }

    @PostMapping("/admin/user/{user-id}/user-documents")
    public int createUserDocument(@RequestBody UserDocumentReq userDocumentReq,
                                  @PathVariable("user-id") String userId) {
        return userDocumentService.createUserDocument(userDocumentReq, userId);
    }

    @PutMapping("/admin/user/{user-id}/user-documents")
    public int updateUserDocument(@RequestBody UserDocumentReq userDocumentReq,
                                  @PathVariable("user-id") String userId) {
        return userDocumentService.updateUserDocument(userDocumentReq, userId);
    }

    @DeleteMapping("/admin/user-documents")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserDocument(@RequestParam String userDocumentId) {
        userDocumentService.deleteUserDocument(userDocumentId);
    }

    @GetMapping("/vehicles/documents")
    public VehicleDocumentRes getVehicleDocuments(
            @RequestParam String vehicleId,
            @RequestParam(defaultValue = "1") int viewOption) {
        return vehicleDocumentService.getVehicleDocuments(vehicleId, viewOption);
    }

    @PostMapping("/vehicles/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public void createVehicleDocument(@RequestBody VehicleDocumentStandaloneReq vehicleDocumentStandaloneReq) {
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

    @GetMapping("/vehicles/documents/{document-id}")
    public VehicleDocumentSingleRes getVehicleDocumentById(@PathVariable("document-id") String vehicleDocId) {
        return vehicleDocumentService.getVehicleDocument(vehicleDocId);
    }
}
