package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.DocumentStatus;
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
                                            @RequestParam(required = false) DocumentStatus documentStatus) {
        return userDocumentService.getUserDocuments(userId, documentStatus);
    }

    @GetMapping("/user-documents/{user-document-number}")
    public UserDocumentDetailRes getUserDocumentDetail(@PathVariable("user-document-number") String userDocumentNumber) {
        return userDocumentService.getUserDocumentDetailById(userDocumentNumber);
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
    public void deleteUserDocument(@RequestParam String userDocumentNumber) {
        userDocumentService.deleteUserDocument(userDocumentNumber);
    }

    @GetMapping("/vehicles/documents")
    public VehicleDocumentRes getVehicleDocuments(
            @RequestParam String vehicleId,
            @RequestParam(defaultValue = "0") int useStatus,
            @RequestParam(required = false) DocumentStatus documentStatus) {
        return vehicleDocumentService.getVehicleDocuments(vehicleId, useStatus, documentStatus);
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
    public void deleteVehicleDocument(@RequestParam int vehicleDocId) {
        vehicleDocumentService.deleteDocument(vehicleDocId);
    }

    @GetMapping("/vehicles/documents/types")
    public VehicleDocumentTypeRes getVehicleDocumentTypes() {
        return new VehicleDocumentTypeRes();
    }

    @GetMapping("/vehicles/documents/{vehicle-document-id}")
    public VehicleDocumentSingleRes getVehicleDocumentById(@PathVariable("vehicle-document-id") int vehicleDocId) {
        return vehicleDocumentService.getVehicleDocument(vehicleDocId);
    }
}
