package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.AuthenticationComponent;
import com.fa20se28.vma.component.DocumentComponent;
import com.fa20se28.vma.component.RequestComponent;
import com.fa20se28.vma.component.impl.UserDocumentComponentImpl;
import com.fa20se28.vma.configuration.exception.RequestAlreadyHandledException;
import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.enums.RequestType;
import com.fa20se28.vma.model.DocumentRequestDetail;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.request.VehicleRequestReq;
import com.fa20se28.vma.response.DocumentRequestDetailRes;
import com.fa20se28.vma.service.RequestService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestComponent requestComponent;
    private final DocumentComponent documentComponent;
    private final AuthenticationComponent authenticationComponent;

    public RequestServiceImpl(RequestComponent requestComponent,
                              UserDocumentComponentImpl documentComponent,
                              AuthenticationComponent authenticationComponent) {
        this.requestComponent = requestComponent;
        this.documentComponent = documentComponent;
        this.authenticationComponent = authenticationComponent;
    }

    @Override
    public int createNewDocumentRequest(RequestReq requestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();
        if (documentComponent.createUserDocumentWithRequest(
                requestReq.getUserDocumentReq(), authentication.getName()) == 1) {
            if (requestComponent
                    .createRequest(
                            requestReq,
                            authentication.getName()) == 1) {
                return 1;
            }
            return 0;
        }
        return 0;
    }

    @Override
    public int createUpdateDocumentRequest(RequestReq requestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();
        if (documentComponent.updateUserDocumentWithRequest(
                requestReq.getUserDocumentReq(), authentication.getName()) == 1) {
            if (requestComponent
                    .createRequest(
                            requestReq,
                            authentication.getName()) == 1) {
                return 1;
            }
            return 0;
        }
        return 0;
    }

    @Override
    public int createDeleteDocumentRequest(RequestReq requestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();
        if (requestComponent
                .createRequest(
                        requestReq,
                        authentication.getName()) == 1) {
            return 1;
        }
        return 0;
    }

    @Override
    public RequestPageRes getPendingRequests(int page) {
        RequestPageRes requestPageRes = new RequestPageRes();
        requestPageRes.setRequestRes(requestComponent.findPendingRequests(page));
        return requestPageRes;
    }

    @Override
    public int getTotalPendingRequests() {
        return requestComponent.findTotalPendingRequests();
    }

    @Override
    public DocumentRequestDetailRes getDocumentRequestById(int requestId) {
        DocumentRequestDetailRes documentRequestDetailRes = new DocumentRequestDetailRes();
        documentRequestDetailRes.setRequestDetail(requestComponent.findDocumentRequestById(requestId));
        return documentRequestDetailRes;
    }

    @Override
    public int updateDocumentRequestStatusByRequestId(int requestId, RequestStatus requestStatus) {
        DocumentRequestDetail documentRequestDetail = requestComponent.findDocumentRequestById(requestId);
        if (!documentRequestDetail.getRequestStatus().equals(RequestStatus.PENDING)) {
            throw new RequestAlreadyHandledException("Request with id: " + requestId + " has already been handled");
        } else {
            if (requestStatus.equals(RequestStatus.ACCEPTED)) {
                return acceptRequest(documentRequestDetail);
            } else if (requestStatus.equals(RequestStatus.DENIED)) {
                return denyRequest(documentRequestDetail);
            }
            return 0;
        }
    }

    private int acceptRequest(DocumentRequestDetail documentRequestDetail) {
        if (documentRequestDetail.getRequestType().equals(RequestType.NEW_DOCUMENT)) {
            if (documentComponent.acceptNewDocumentRequest(documentRequestDetail.getUserDocumentId()) == 1) {
                return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
            }
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.UPDATE_DOCUMENT)) {
            if (documentComponent.acceptNewDocumentRequest(documentRequestDetail.getUserDocumentId()) == 1) {
                return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
            }
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.DELETE_DOCUMENT)) {
            documentComponent.deleteUserDocument(documentRequestDetail.getUserDocumentId());
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        return 0;
    }

    private int denyRequest(DocumentRequestDetail documentRequestDetail) {
        if (documentRequestDetail.getRequestType().equals(RequestType.NEW_DOCUMENT)) {
            documentComponent.deleteUserDocument(documentRequestDetail.getUserDocumentId());
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.UPDATE_DOCUMENT)) {
            if (documentComponent.denyUpdateDocumentRequest(documentRequestDetail.getUserDocumentId()) == 1) {
                return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
            }
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.DELETE_DOCUMENT)) {
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
        }
        return 0;
    }

    @Override
    public int createVehicleDocumentRequest(VehicleRequestReq vehicleRequestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();
        if (requestComponent.createVehicleDocumentRequest(vehicleRequestReq, authentication.getName()) == 1) {
            return 1;
        }
        return 0;
    }
}
