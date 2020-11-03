package com.fa20se28.vma.controller;

import com.fa20se28.vma.enums.RequestType;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    //TODO: make a main controller for request using RequestTypeEnum to redirect
    @PostMapping("/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public int createRequest(@RequestBody RequestReq requestReq) {
        if (requestReq.getRequestType() != null) {
            if (requestReq.getRequestType() == RequestType.ADD_NEW_VEHICLE) {
                return requestService.createNewDocumentRequest(requestReq);
            } else if (requestReq.getRequestType() == RequestType.NEW_DOCUMENT) {
                return requestService.createUpdateDocumentRequest(requestReq);
            } else if (requestReq.getRequestType() == RequestType.DELETE_DOCUMENT) {
                return requestService.createDeleteDocumentRequest(requestReq);
            }
            return 0;
        }
        return 0;
    }
}
