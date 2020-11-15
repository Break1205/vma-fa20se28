package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.*;
import com.fa20se28.vma.component.impl.UserDocumentComponentImpl;
import com.fa20se28.vma.configuration.exception.RequestAlreadyHandledException;
import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.enums.RequestType;
import com.fa20se28.vma.model.AssignedVehicle;
import com.fa20se28.vma.model.DocumentRequestDetail;
import com.fa20se28.vma.request.*;
import com.fa20se28.vma.response.DocumentRequestDetailRes;
import com.fa20se28.vma.response.RequestPageRes;
import com.fa20se28.vma.service.RequestService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestComponent requestComponent;
    private final UserDocumentComponent userDocumentComponent;
    private final VehicleComponent vehicleComponent;
    private final VehicleDocumentComponent vehicleDocumentComponent;
    private final AuthenticationComponent authenticationComponent;

    public RequestServiceImpl(RequestComponent requestComponent,
                              UserDocumentComponentImpl documentComponent,
                              VehicleComponent vehicleComponent, VehicleDocumentComponent vehicleDocumentComponent, AuthenticationComponent authenticationComponent) {
        this.requestComponent = requestComponent;
        this.userDocumentComponent = documentComponent;
        this.vehicleComponent = vehicleComponent;
        this.vehicleDocumentComponent = vehicleDocumentComponent;
        this.authenticationComponent = authenticationComponent;
    }

    @Override
    public int createNewDocumentRequest(RequestReq requestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();
        if (userDocumentComponent.createUserDocumentWithRequest(
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
        if (userDocumentComponent.updateUserDocumentWithRequest(
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
    public RequestPageRes getRequests(RequestPageReq requestPageReq) {
        RequestPageRes requestPageRes = new RequestPageRes();
        requestPageRes.setRequestRes(requestComponent.findRequests(requestPageReq));
        return requestPageRes;
    }

    @Override
    public int getTotalRequests(RequestPageReq requestPageReq) {
        return requestComponent.findTotalRequests(requestPageReq);
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
            if (userDocumentComponent.acceptNewDocumentRequest(documentRequestDetail.getUserDocumentId()) == 1) {
                return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
            }
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.UPDATE_DOCUMENT)) {
            if (userDocumentComponent.acceptNewDocumentRequest(documentRequestDetail.getUserDocumentId()) == 1) {
                return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
            }
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.DELETE_DOCUMENT)) {
            userDocumentComponent.deleteUserDocument(documentRequestDetail.getUserDocumentId());
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.NEW_VEHICLE_DOCUMENT)) {
            vehicleDocumentComponent.acceptDocument(documentRequestDetail.getVehicleDocumentId());
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.DELETE_VEHICLE_DOCUMENT)) {
            vehicleDocumentComponent.deleteDocument(documentRequestDetail.getVehicleDocumentId());
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.ADD_NEW_VEHICLE)) {
            vehicleComponent.acceptVehicle(documentRequestDetail.getVehicleId());
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.WITHDRAW_VEHICLE)) {
            vehicleComponent.deleteVehicle(documentRequestDetail.getVehicleId());
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.CHANGE_VEHICLE)) {
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }

        return 0;
    }

    private int denyRequest(DocumentRequestDetail documentRequestDetail) {
        if (documentRequestDetail.getRequestType().equals(RequestType.NEW_DOCUMENT)) {
            userDocumentComponent.deleteUserDocument(documentRequestDetail.getUserDocumentId());
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.UPDATE_DOCUMENT)) {
            if (userDocumentComponent.denyUpdateDocumentRequest(documentRequestDetail.getUserDocumentId()) == 1) {
                return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
            }
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.DELETE_DOCUMENT)) {
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.NEW_VEHICLE_DOCUMENT)) {
            vehicleDocumentComponent.denyDocument(
                    documentRequestDetail.getRequestId(),
                    documentRequestDetail.getVehicleId(),
                    documentRequestDetail.getVehicleDocumentId());
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.DELETE_VEHICLE_DOCUMENT)) {
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.ADD_NEW_VEHICLE)) {
            vehicleComponent.denyVehicle(documentRequestDetail.getVehicleId(), documentRequestDetail.getRequestId());
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.WITHDRAW_VEHICLE)) {
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (documentRequestDetail.getRequestType().equals(RequestType.CHANGE_VEHICLE)) {
            return requestComponent.updateRequestStatus(documentRequestDetail.getRequestId(), RequestStatus.DENIED);
        }
        return 0;
    }

    @Override
    public int createVehicleDocumentRequest(VehicleDocumentRequestReq vehicleDocumentRequestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();

        if (requestComponent.createVehicleDocumentRequest(vehicleDocumentRequestReq, authentication.getName()) == 1) {
            if (vehicleDocumentRequestReq.getRequestType().equals(RequestType.NEW_VEHICLE_DOCUMENT)) {
                vehicleDocumentComponent.createVehicleDocumentFromRequest(vehicleDocumentRequestReq.getVehicleDocument());
            }

            return 1;
        }

        return 0;
    }

    @Override
    public int createVehicleRequest(VehicleRequestReq vehicleRequestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();

        if (vehicleRequestReq.getRequestType().equals(RequestType.ADD_NEW_VEHICLE)) {
            vehicleComponent.createVehicleFromRequest(vehicleRequestReq.getVehicleReq());
        }

        if (requestComponent.createVehicleRequest(vehicleRequestReq, authentication.getName()) == 1) {
            return 1;
        }

        return 0;
    }

    @Override
    public int createVehicleChangeRequest(VehicleChangeRequestReq vehicleChangeRequestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();

        if (requestComponent.createVehicleChangeRequest(vehicleChangeRequestReq, authentication.getName()) == 1) {
            return 1;
        }

        return 0;
    }

    @Override
    public int acceptVehicleChangeRequest(String driverId, String targetVehicleId) {
        AssignedVehicle assignedVehicle = vehicleComponent.getCurrentlyAssignedVehicle(driverId);

        if (assignedVehicle != null) {
            vehicleComponent.withdrawVehicle(assignedVehicle.getVehicleId());
            vehicleComponent.assignVehicle(targetVehicleId, driverId);

            return 1;
        }
        return 0;
    }
}
