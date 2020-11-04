package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.enums.RequestType;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.request.VehicleRequestReq;
import com.fa20se28.vma.response.DocumentRequestDetailRes;
import com.fa20se28.vma.response.RequestTypesRes;
import com.fa20se28.vma.service.RequestService;
import com.fa20se28.vma.service.impl.RequestPageRes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public RequestTypesRes getRequestTypes(){
        return new RequestTypesRes();
    }

    @GetMapping("/requests")
    public RequestPageRes getPendingRequest(@RequestParam(defaultValue = "0") int page) {
        return requestService.getPendingRequests(page * 15);
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
