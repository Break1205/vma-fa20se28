package com.fa20se28.vma.service;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.response.DocumentRequestDetailRes;
import com.fa20se28.vma.response.RequestPageRes;
import com.fa20se28.vma.service.impl.RequestPageRes;
import com.fa20se28.vma.request.VehicleRequestReq;

public interface RequestService {
    int createNewDocumentRequest(RequestReq requestReq);

    int createUpdateDocumentRequest(RequestReq requestReq);

    int createDeleteDocumentRequest(RequestReq requestReq);

    RequestPageRes getPendingRequests(int page);

    int getTotalPendingRequests();

    DocumentRequestDetailRes getDocumentRequestById(int requestId);

    int updateDocumentRequestStatusByRequestId(int requestId, RequestStatus requestStatus);

    int createVehicleDocumentRequest(VehicleRequestReq vehicleRequestReq);
}
