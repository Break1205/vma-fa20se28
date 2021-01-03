package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.AuthenticationComponent;
import com.fa20se28.vma.component.RequestComponent;
import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.component.UserDocumentComponent;
import com.fa20se28.vma.component.VehicleComponent;
import com.fa20se28.vma.component.VehicleDocumentComponent;
import com.fa20se28.vma.component.impl.UserDocumentComponentImpl;
import com.fa20se28.vma.configuration.exception.RequestAlreadyHandledException;
import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.enums.NotificationType;
import com.fa20se28.vma.enums.RequestStatus;
import com.fa20se28.vma.enums.RequestType;
import com.fa20se28.vma.enums.VehicleStatus;
import com.fa20se28.vma.model.AssignedVehicle;
import com.fa20se28.vma.model.ClientRegistrationToken;
import com.fa20se28.vma.model.NotificationData;
import com.fa20se28.vma.model.RequestDetail;
import com.fa20se28.vma.request.ReportIssueReq;
import com.fa20se28.vma.request.RequestPageReq;
import com.fa20se28.vma.request.RequestReq;
import com.fa20se28.vma.request.VehicleChangeRequestReq;
import com.fa20se28.vma.request.VehicleDocumentRequestReq;
import com.fa20se28.vma.request.VehicleRequestReq;
import com.fa20se28.vma.response.RequestDetailRes;
import com.fa20se28.vma.response.RequestPageRes;
import com.fa20se28.vma.service.FirebaseService;
import com.fa20se28.vma.service.RequestService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestComponent requestComponent;
    private final UserDocumentComponent userDocumentComponent;
    private final VehicleComponent vehicleComponent;
    private final VehicleDocumentComponent vehicleDocumentComponent;
    private final AuthenticationComponent authenticationComponent;
    private final UserComponent userComponent;
    private final FirebaseService firebaseService;

    public RequestServiceImpl(RequestComponent requestComponent,
                              UserDocumentComponentImpl documentComponent,
                              VehicleComponent vehicleComponent,
                              VehicleDocumentComponent vehicleDocumentComponent,
                              AuthenticationComponent authenticationComponent,
                              UserComponent userComponent,
                              FirebaseService firebaseService) {
        this.requestComponent = requestComponent;
        this.userDocumentComponent = documentComponent;
        this.vehicleComponent = vehicleComponent;
        this.vehicleDocumentComponent = vehicleDocumentComponent;
        this.authenticationComponent = authenticationComponent;
        this.userComponent = userComponent;
        this.firebaseService = firebaseService;
    }

    private int createNotificationForAdmin(int requestId, String userId, RequestType requestType) {
        if (requestId >= 1) {
            NotificationData createReq = new NotificationData(
                    NotificationType.NEW_REQUEST,
                    "User with id: " + userId + " has sent a new request!",
                    String.valueOf(requestId),
                    requestType.toString());
            firebaseService.notifySubscribersByTopic("admin", createReq);
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public int createNewDocumentRequest(RequestReq requestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();

        if (userDocumentComponent.createUserDocumentWithRequest(
                requestReq.getUserDocumentReq(), authentication.getName(), DocumentStatus.PENDING) == 1) {
            createNotificationForAdmin(
                    requestComponent.createRequest(requestReq, authentication.getName()),
                    authentication.getName(),
                    requestReq.getRequestType());
        }
        return 0;
    }

    @Override
    @Transactional
    public int createUpdateDocumentRequest(RequestReq requestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();

        if (userDocumentComponent.createUpdateUserDocumentWithRequest(
                requestReq.getUserDocumentReq(), authentication.getName(), DocumentStatus.PENDING) == 1) {
            createNotificationForAdmin(
                    requestComponent.createRequest(requestReq, authentication.getName()),
                    authentication.getName(),
                    requestReq.getRequestType());
        }
        return 0;
    }

    @Override
    @Transactional
    public int createDeleteDocumentRequest(RequestReq requestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();

        return createNotificationForAdmin(
                requestComponent.createRequest(requestReq, authentication.getName()),
                authentication.getName(),
                requestReq.getRequestType());
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
    public RequestDetailRes getRequestById(int requestId) {
        RequestDetailRes requestDetailRes = new RequestDetailRes();
        requestDetailRes.setRequestDetail(requestComponent.findRequestById(requestId));
        return requestDetailRes;
    }

    @Override
    @Transactional
    public int updateDocumentRequestStatusByRequestId(int requestId, RequestStatus requestStatus) {
        RequestDetail requestDetail = requestComponent.findRequestById(requestId);
        if (!requestDetail.getRequestStatus().equals(RequestStatus.PENDING)) {
            throw new RequestAlreadyHandledException("Request with id: " + requestId + " has already been handled");
        } else {
            ClientRegistrationToken clientRegistrationToken = userComponent.findClientRegistrationTokenByUserId(requestDetail.getUserId());
            if (requestStatus.equals(RequestStatus.ACCEPTED)) {
                if (acceptRequest(requestDetail) == 1) {
                    if (clientRegistrationToken == null) {
                        return 0;
                    }
                    NotificationData notificationData = new NotificationData(
                            NotificationType.REQUEST_ACCEPTED,
                            "Request " + requestDetail.getRequestType() + " with id " + requestId + " has been accepted",
                            String.valueOf(requestId),
                            null);
                    firebaseService.notifyUserByFCMToken(clientRegistrationToken, notificationData);
                    return 1;
                }
                return 0;
            } else if (requestStatus.equals(RequestStatus.DENIED)) {
                if (denyRequest(requestDetail) == 1) {
                    if (clientRegistrationToken == null) {
                        return 0;
                    }
                    NotificationData notificationData = new NotificationData(
                            NotificationType.REQUEST_DENIED,
                            "Request " + requestDetail.getRequestType() + " with id " + requestId + " has been denied",
                            String.valueOf(requestId),
                            null);
                    firebaseService.notifyUserByFCMToken(clientRegistrationToken, notificationData);
                    return 1;
                }
                return 0;
            }
            return 0;
        }
    }

    private int acceptRequest(RequestDetail requestDetail) {
        if (requestDetail.getRequestType().equals(RequestType.NEW_DOCUMENT)) {
            if (userDocumentComponent.acceptNewDocumentRequest(requestDetail.getUserDocumentId()) == 1) {
                return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.ACCEPTED);
            }
        }
        if (requestDetail.getRequestType().equals(RequestType.UPDATE_DOCUMENT)) {
            if (userDocumentComponent.acceptNewDocumentRequest(requestDetail.getUserDocumentId()) == 1) {
                return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.ACCEPTED);
            }
        }
        if (requestDetail.getRequestType().equals(RequestType.DELETE_DOCUMENT)) {
            userDocumentComponent.deleteUserDocumentWithRequest(requestDetail.getUserDocumentId());
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (requestDetail.getRequestType().equals(RequestType.NEW_VEHICLE_DOCUMENT)) {
            vehicleDocumentComponent.acceptDocument(requestDetail.getVehicleDocumentId());
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (requestDetail.getRequestType().equals(RequestType.DELETE_VEHICLE_DOCUMENT)) {
            vehicleDocumentComponent.deleteDocument(requestDetail.getVehicleDocumentId());
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (requestDetail.getRequestType().equals(RequestType.ADD_NEW_VEHICLE)) {
            vehicleComponent.acceptVehicle(requestDetail.getVehicleId());
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (requestDetail.getRequestType().equals(RequestType.WITHDRAW_VEHICLE)) {
            vehicleComponent.deleteVehicle(requestDetail.getVehicleId());
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (requestDetail.getRequestType().equals(RequestType.CHANGE_VEHICLE)) {
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        if (requestDetail.getRequestType().equals(RequestType.VEHICLE_NEEDS_REPAIR)) {
            vehicleComponent.updateVehicleStatus(requestDetail.getVehicleId(), VehicleStatus.NEED_REPAIR);
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.ACCEPTED);
        }
        return 0;
    }

    private int denyRequest(RequestDetail requestDetail) {
        if (requestDetail.getRequestType().equals(RequestType.NEW_DOCUMENT)) {
            if (userDocumentComponent.denyUpdateDocumentRequest(requestDetail.getUserDocumentId()) == 1) {
                return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.DENIED);
            }
        }
        if (requestDetail.getRequestType().equals(RequestType.UPDATE_DOCUMENT)) {
            if (userDocumentComponent.denyUpdateDocumentRequest(requestDetail.getUserDocumentId()) == 1) {
                return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.DENIED);
            }
        }
        if (requestDetail.getRequestType().equals(RequestType.DELETE_DOCUMENT)) {
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (requestDetail.getRequestType().equals(RequestType.NEW_VEHICLE_DOCUMENT)) {
            vehicleDocumentComponent.denyDocument(requestDetail.getVehicleDocumentId());
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (requestDetail.getRequestType().equals(RequestType.DELETE_VEHICLE_DOCUMENT)) {
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (requestDetail.getRequestType().equals(RequestType.ADD_NEW_VEHICLE)) {
            vehicleComponent.denyVehicle(requestDetail.getVehicleId());
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (requestDetail.getRequestType().equals(RequestType.WITHDRAW_VEHICLE)) {
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (requestDetail.getRequestType().equals(RequestType.CHANGE_VEHICLE)) {
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.DENIED);
        }
        if (requestDetail.getRequestType().equals(RequestType.VEHICLE_NEEDS_REPAIR)) {
            return requestComponent.updateRequestStatus(requestDetail.getRequestId(), RequestStatus.DENIED);
        }
        return 0;
    }

    @Override
    public int createVehicleDocumentRequest(VehicleDocumentRequestReq vehicleDocumentRequestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();

        if (vehicleDocumentRequestReq.getRequestType().equals(RequestType.NEW_VEHICLE_DOCUMENT)) {
            vehicleDocumentComponent.createVehicleDocument(vehicleDocumentRequestReq.getVehicleDocument(), true);
        }

        return createNotificationForAdmin(
                requestComponent.createVehicleDocumentRequest(vehicleDocumentRequestReq, authentication.getName()),
                authentication.getName(),
                vehicleDocumentRequestReq.getRequestType());
    }

    @Override
    public int createVehicleRequest(VehicleRequestReq vehicleRequestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();

        if (vehicleRequestReq.getRequestType().equals(RequestType.ADD_NEW_VEHICLE)) {
            vehicleComponent.createVehicleFromRequest(vehicleRequestReq.getVehicleReq());
        }

        return createNotificationForAdmin(
                requestComponent.createVehicleRequest(vehicleRequestReq, authentication.getName()),
                authentication.getName(),
                vehicleRequestReq.getRequestType());
    }

    @Override
    public int createVehicleChangeRequest(VehicleChangeRequestReq vehicleChangeRequestReq) {
        Authentication authentication = authenticationComponent.getAuthentication();

        return createNotificationForAdmin(
                requestComponent.createVehicleChangeRequest(vehicleChangeRequestReq, authentication.getName()),
                authentication.getName(),
                vehicleChangeRequestReq.getRequestType());
    }

    @Override
    public int acceptVehicleChangeRequest(String driverId, String targetVehicleId) {
        AssignedVehicle assignedVehicle = vehicleComponent.getCurrentlyAssignedVehicle(driverId);

        if (assignedVehicle != null) {
            vehicleComponent.withdrawVehicle(assignedVehicle.getVehicleId());
            vehicleComponent.assignVehicle(targetVehicleId, driverId);

            NotificationData notificationData = new NotificationData(
                    NotificationType.VEHICLE_CHANGED,
                    "You have been reassigned to a new vehicle with ID " + targetVehicleId,
                    String.valueOf(targetVehicleId), null);
            firebaseService.notifyUserByFCMToken(
                    userComponent.findClientRegistrationTokenByUserId(driverId),
                    notificationData);

            return 1;
        }
        return 0;
    }

    @Override
    public int reportIssue(ReportIssueReq reportIssueReq) {
        Authentication authentication = authenticationComponent.getAuthentication();

        return createNotificationForAdmin(
                requestComponent.reportIssueRequest(reportIssueReq, authentication.getName()),
                authentication.getName(),
                reportIssueReq.getRequestType());
    }
}
