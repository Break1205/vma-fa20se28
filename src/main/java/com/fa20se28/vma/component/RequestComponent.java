package com.fa20se28.vma.component;

import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.model.DocumentRequestDetail;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.response.RequestRes;

import java.util.List;

public interface RequestComponent {
    int createRequest(RequestReq requestReq, String userId);

    List<RequestRes> findPendingRequests(int page);

    int findTotalPendingRequests();

    DocumentRequestDetail findDocumentRequestById(int requestId);

    int updateRequestStatus(int requestId, RequestStatus accepted);
}
