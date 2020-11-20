package com.fa20se28.vma.service;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.RequestDetailRes;
import com.fa20se28.vma.response.RequestPageRes;

public interface RequestService {
    int createNewDocumentRequest(RequestReq requestReq);

    int createUpdateDocumentRequest(RequestReq requestReq);

    int createDeleteDocumentRequest(RequestReq requestReq);

    RequestPageRes getRequests(RequestPageReq requestPageReq);

    int getTotalRequests(RequestPageReq requestPageReq);

    RequestDetailRes getRequestById(int requestId);

    int updateDocumentRequestStatusByRequestId(int requestId, RequestStatus requestStatus);

    int createVehicleDocumentRequest(VehicleDocumentRequestReq vehicleDocumentRequestReq);

    int createVehicleRequest(VehicleRequestReq vehicleRequestReq);

    int createVehicleChangeRequest(VehicleChangeRequestReq vehicleChangeRequestReq);

    int acceptVehicleChangeRequest(String driverId, String targetVehicleId);
}
