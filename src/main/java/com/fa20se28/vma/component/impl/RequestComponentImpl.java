package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.RequestComponent;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.mapper.RequestMapper;
import com.fa20se28.vma.model.DocumentRequestDetail;
import com.fa20se28.vma.request.RequestPageReq;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.request.VehicleRequestReq;
import com.fa20se28.vma.response.RequestRes;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class RequestComponentImpl implements RequestComponent {
    private final RequestMapper requestMapper;

    public RequestComponentImpl(RequestMapper requestMapper) {
        this.requestMapper = requestMapper;
    }

    @Override
    @Transactional
    public int createRequest(RequestReq requestReq, String userId) {
        return requestMapper.insertRequest(requestReq, userId);
    }

    @Override
    public List<RequestRes> findPendingRequests(RequestPageReq requestPageReq) {
        return requestMapper.findPendingRequest(requestPageReq);
    }

    @Override
    public int findTotalPendingRequests() {
        return requestMapper.findTotalPendingRequests();
    }

    @Override
    public DocumentRequestDetail findDocumentRequestById(int requestId) {
        Optional<DocumentRequestDetail> optionalDocumentRequestDetail =
                requestMapper.findDocumentRequestById(requestId);
        return optionalDocumentRequestDetail.orElseThrow(
                () -> new ResourceNotFoundException("Request with id: " + requestId + " not found"));
    }

    @Override
    @Transactional
    public int updateRequestStatus(int requestId, RequestStatus requestStatus) {
        return requestMapper.updateRequestStatus(requestId, requestStatus);
    }

    @Override
    @Transactional
    public int createVehicleDocumentRequest(VehicleRequestReq vehicleRequestReq, String userId) {
        return requestMapper.insertVehicleRequest(vehicleRequestReq, RequestStatus.PENDING, userId);
    }
}
