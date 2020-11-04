package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.RequestComponent;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.mapper.RequestMapper;
import com.fa20se28.vma.model.DocumentRequestDetail;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.response.RequestRes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RequestComponentImpl implements RequestComponent {
    private final RequestMapper requestMapper;

    public RequestComponentImpl(RequestMapper requestMapper) {
        this.requestMapper = requestMapper;
    }

    @Override
    public int createRequest(RequestReq requestReq, String userId) {
        return requestMapper.insertRequest(requestReq, userId);
    }

    @Override
    public List<RequestRes> findPendingRequests(int page) {
        return requestMapper.findPendingRequest(page);
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
    public int updateRequestStatus(int requestId, RequestStatus requestStatus) {
        return requestMapper.updateRequestStatus(requestId, requestStatus);
    }
}
