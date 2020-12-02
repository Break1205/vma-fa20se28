package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.RequestComponent;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.mapper.RequestMapper;
import com.fa20se28.vma.model.RequestDetail;
import com.fa20se28.vma.request.*;
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
        return requestMapper.insertRequest(
                userId,
                requestReq.getUserDocumentReq().getUserDocumentId(),
                null, null,
                RequestStatus.PENDING,
                requestReq.getRequestType(),
                requestReq.getDescription());
    }

    @Override
    public List<RequestRes> findRequests(RequestPageReq requestPageReq) {
        return requestMapper.findRequests(requestPageReq);
    }

    @Override
    public int findTotalRequests(RequestPageReq requestPageReq) {
        return requestMapper.findTotalRequests(requestPageReq);
    }

    @Override
    public RequestDetail findRequestById(int requestId) {
        Optional<RequestDetail> optionalDocumentRequestDetail =
                requestMapper.findRequestById(requestId);
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
    public int createVehicleDocumentRequest(VehicleDocumentRequestReq vehicleDocumentRequestReq, String userId) {
        return requestMapper.insertRequest(
                userId, null,
                vehicleDocumentRequestReq.getVehicleDocument().getVehicleId(),
                vehicleDocumentRequestReq.getVehicleDocument().getVehicleDocumentReq().getVehicleDocumentId(),
                RequestStatus.PENDING,
                vehicleDocumentRequestReq.getRequestType(),
                vehicleDocumentRequestReq.getDescription());
    }

    @Override
    @Transactional
    public int createVehicleRequest(VehicleRequestReq vehicleRequestReq, String userId) {
        return requestMapper.insertRequest(
                userId, null,
                vehicleRequestReq.getVehicleReq().getVehicleId(),
                null,
                RequestStatus.PENDING,
                vehicleRequestReq.getRequestType(),
                vehicleRequestReq.getDescription());
    }

    @Override
    @Transactional
    public int createVehicleChangeRequest(VehicleChangeRequestReq vehicleChangeRequestReq, String userId) {
        return requestMapper.insertRequest(
                userId, null, null, null,
                RequestStatus.PENDING,
                vehicleChangeRequestReq.getRequestType(),
                vehicleChangeRequestReq.getDescription());
    }

    @Override
    @Transactional
    public int reportIssueRequest(ReportIssueReq reportIssueReq, String userId) {
        return requestMapper.insertRequest(
                userId, null,
                reportIssueReq.getVehicleId(), null,
                RequestStatus.PENDING,
                reportIssueReq.getRequestType(),
                reportIssueReq.getDescription());
    }
}
