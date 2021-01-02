package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.RequestComponent;
import com.fa20se28.vma.configuration.exception.DataExecutionException;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.mapper.RequestMapper;
import com.fa20se28.vma.model.RequestDetail;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.RequestRes;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        ReqInsertReq insertReq = new ReqInsertReq(
                userId,
                String.valueOf(requestReq.getUserDocumentReq().getUserDocumentId()),
                null, 0,
                RequestStatus.PENDING,
                requestReq.getRequestType(),
                requestReq.getDescription(),
                LocalDateTime.now());

        int row = requestMapper.insertRequest(insertReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }

        return insertReq.getRequestId();
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
        ReqInsertReq insertReq = new ReqInsertReq(
                userId, null,
                null,
                vehicleDocumentRequestReq.getVehicleDocument().getVehicleDocumentReq().getVehicleDocumentId(),
                RequestStatus.PENDING,
                vehicleDocumentRequestReq.getRequestType(),
                vehicleDocumentRequestReq.getDescription(),
                LocalDateTime.now());

        int row = requestMapper.insertRequest(insertReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }

        return insertReq.getRequestId();
    }

    @Override
    @Transactional
    public int createVehicleRequest(VehicleRequestReq vehicleRequestReq, String userId) {
        ReqInsertReq insertReq = new ReqInsertReq(
                userId, null,
                vehicleRequestReq.getVehicleReq().getVehicleId(),
                0,
                RequestStatus.PENDING,
                vehicleRequestReq.getRequestType(),
                vehicleRequestReq.getDescription(),
                LocalDateTime.now());

        int row = requestMapper.insertRequest(insertReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }

        return insertReq.getRequestId();
    }

    @Override
    @Transactional
    public int createVehicleChangeRequest(VehicleChangeRequestReq vehicleChangeRequestReq, String userId) {
        ReqInsertReq insertReq = new ReqInsertReq(
                userId, null, null, 0,
                RequestStatus.PENDING,
                vehicleChangeRequestReq.getRequestType(),
                vehicleChangeRequestReq.getDescription(),
                LocalDateTime.now());

        int row = requestMapper.insertRequest(insertReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }

        return insertReq.getRequestId();
    }

    @Override
    @Transactional
    public int reportIssueRequest(ReportIssueReq reportIssueReq, String userId) {
        ReqInsertReq insertReq = new ReqInsertReq(
                userId, null,
                reportIssueReq.getVehicleId(), 0,
                RequestStatus.PENDING,
                reportIssueReq.getRequestType(),
                reportIssueReq.getDescription(),
                LocalDateTime.now());

        int row = requestMapper.insertRequest(insertReq);

        if (row == 0) {
            throw new DataExecutionException("Unknown error occurred. Data not modified!");
        }

        return insertReq.getRequestId();
    }
}
