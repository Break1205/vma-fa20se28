package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.enums.RequestType;
import com.fa20se28.vma.request.RequestPageReq;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.request.VehicleRequestReq;
import com.fa20se28.vma.response.DocumentRequestDetailRes;
import com.fa20se28.vma.response.RequestTypesRes;
import com.fa20se28.vma.service.RequestService;
import com.fa20se28.vma.response.RequestPageRes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/document-requests")
    @ResponseStatus(HttpStatus.CREATED)
    public int createRequest(@RequestBody RequestReq requestReq) {
        if (requestReq.getRequestType() != null) {
            if (requestReq.getRequestType() == RequestType.NEW_DOCUMENT) {
                return requestService.createNewDocumentRequest(requestReq);
            } else if (requestReq.getRequestType() == RequestType.UPDATE_DOCUMENT) {
                return requestService.createUpdateDocumentRequest(requestReq);
            } else if (requestReq.getRequestType() == RequestType.DELETE_DOCUMENT) {
                return requestService.createDeleteDocumentRequest(requestReq);
            }
            return 0;
        }
        return 0;
    }

    @GetMapping("/requests/types")
    public RequestTypesRes getRequestTypes() {
        return new RequestTypesRes();
    }

    @GetMapping("/requests")
    public RequestPageRes getPendingRequest(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(required = false) String userId,
                                            @RequestParam(required = false) RequestType requestType,
                                            @RequestParam(required = false) String fromDate,
                                            @RequestParam(required = false) String toDate) {
        return requestService.getPendingRequests(new RequestPageReq(userId, requestType, fromDate, toDate, page * 15));
    }

    @GetMapping("/requests/count")
    public int getTotalPendingRequest() {
        return requestService.getTotalPendingRequests();
    }

    @GetMapping("/document-requests/{request-id}")
    public DocumentRequestDetailRes getDocumentRequestById(@PathVariable("request-id") int requestId) {
        return requestService.getDocumentRequestById(requestId);
    }

    @PatchMapping("/document-requests/{request-id}")
    public int updateDocumentRequestStatusByRequestId(@PathVariable("request-id") int requestId,
                                                      @RequestParam RequestStatus requestStatus) {
        return requestService.updateDocumentRequestStatusByRequestId(requestId, requestStatus);
    }

    @PostMapping("/vehicle-document-requests")
    @ResponseStatus(HttpStatus.CREATED)
    public int createVehicleRequest(@RequestBody VehicleRequestReq vehicleRequestReq) {
        if (vehicleRequestReq.getRequestType() != null) {
            return requestService.createVehicleDocumentRequest(vehicleRequestReq);
        }
        return 0;
    }
}
