package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.model.DocumentRequestDetail;
import com.fa20se28.vma.request.RequestPageReq;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.request.VehicleRequestReq;
import com.fa20se28.vma.response.RequestRes;

import java.util.List;

public interface RequestComponent {
    int createRequest(RequestReq requestReq, String userId);

    List<RequestRes> findRequests(RequestPageReq page);

    int findTotalRequests(RequestPageReq requestPageReq);

    DocumentRequestDetail findDocumentRequestById(int requestId);

    int updateRequestStatus(int requestId, RequestStatus accepted);

    int createVehicleDocumentRequest(VehicleRequestReq vehicleRequestReq, String userId);
}
