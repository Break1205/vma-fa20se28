package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.model.RequestDetail;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.RequestRes;

import java.util.List;

public interface RequestComponent {
    int createRequest(RequestReq requestReq, String userId);

    List<RequestRes> findRequests(RequestPageReq page);

    int findTotalRequests(RequestPageReq requestPageReq);

    RequestDetail findRequestById(int requestId);

    int updateRequestStatus(int requestId, RequestStatus accepted);

    int createVehicleDocumentRequest(VehicleDocumentRequestReq vehicleDocumentRequestReq, String userId);

    int createVehicleRequest(VehicleRequestReq vehicleRequestReq, String userId);

    int createVehicleChangeRequest(VehicleChangeRequestReq vehicleChangeRequestReq, String userId);
}
