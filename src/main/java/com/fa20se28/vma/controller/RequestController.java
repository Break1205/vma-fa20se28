package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.enums.RequestType;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.RequestDetailRes;
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

@RestController
@RequestMapping("/api/v1")
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/requests/users/document")
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
    public RequestPageRes getRequests(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(required = false) String userId,
                                      @RequestParam(required = false) RequestType requestType,
                                      @RequestParam(required = false) RequestStatus requestStatus,
                                      @RequestParam(required = false) String fromDate,
                                      @RequestParam(required = false) String toDate) {
        return requestService.getRequests(new RequestPageReq(userId, requestType, requestStatus, fromDate, toDate, page * 15));
    }

    @GetMapping("/requests/count")
    public int getTotalRequests(@RequestParam(required = false) String userId,
                                @RequestParam(required = false) RequestType requestType,
                                @RequestParam(required = false) RequestStatus requestStatus,
                                @RequestParam(required = false) String fromDate,
                                @RequestParam(required = false) String toDate) {
        return requestService.getTotalRequests(new RequestPageReq(userId, requestType, requestStatus, fromDate, toDate, 0));
    }

    @GetMapping("/requests/{request-id}")
    public RequestDetailRes getRequestById(@PathVariable("request-id") int requestId) {
        return requestService.getRequestById(requestId);
    }

    @PatchMapping("/requests/{request-id}")
    public int updateRequestStatusByRequestId(@PathVariable("request-id") int requestId,
                                              @RequestParam RequestStatus requestStatus) {
        return requestService.updateDocumentRequestStatusByRequestId(requestId, requestStatus);
    }

    @PostMapping("/requests/vehicles/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public int createVehicleDocumentRequest(@RequestBody VehicleDocumentRequestReq vehicleDocumentRequestReq) {
        if (vehicleDocumentRequestReq.getRequestType() != null) {
            return requestService.createVehicleDocumentRequest(vehicleDocumentRequestReq);
        }
        return 0;
    }

    @PostMapping("/requests/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    public int createVehicleRequest(@RequestBody VehicleRequestReq vehicleRequestReq) {
        if (vehicleRequestReq.getRequestType() != null) {
            return requestService.createVehicleRequest(vehicleRequestReq);
        }
        return 0;
    }

    @PostMapping("/requests/vehicles/change")
    @ResponseStatus(HttpStatus.CREATED)
    public int createVehicleChangeRequest(@RequestBody VehicleChangeRequestReq vehicleChangeRequestReq) {
        if (vehicleChangeRequestReq.getRequestType() != null) {
            return requestService.createVehicleChangeRequest(vehicleChangeRequestReq);
        }
        return 0;
    }

    @PatchMapping("/requests/{request-id}/change")
    @ResponseStatus(HttpStatus.CREATED)
    public int acceptVehicleChangeRequest(@PathVariable("request-id") int requestId,
                                   @RequestParam String driverId,
                                   @RequestParam String targetVehicleId,
                                   @RequestParam RequestStatus requestStatus) {
        if (requestService.acceptVehicleChangeRequest(driverId, targetVehicleId) == 1) {
            return requestService.updateDocumentRequestStatusByRequestId(requestId, requestStatus);
        }
        return 0;
    }
}
