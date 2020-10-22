package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.DriverComponent;
import com.fa20se28.vma.configuration.exception.InvalidRequestException;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.mapper.DocumentImageMapper;
import com.fa20se28.vma.mapper.DriverMapper;
import com.fa20se28.vma.mapper.RequestMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.model.DriverDetail;
import com.fa20se28.vma.model.Request;
import com.fa20se28.vma.request.DocumentImageReq;
import com.fa20se28.vma.request.DriverPageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserDocumentReq;
import com.fa20se28.vma.response.DriverRes;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Component
public class DriverComponentImpl implements DriverComponent {
    private final UserMapper userMapper;
    private final DriverMapper driverMapper;
    private final UserDocumentMapper userDocumentMapper;
    private final DocumentImageMapper documentImageMapper;
    private final RequestMapper requestMapper;

    public DriverComponentImpl(DriverMapper driverMapper,
                               UserDocumentMapper userDocumentMapper,
                               DocumentImageMapper documentImageMapper,
                               UserMapper userMapper,
                               RequestMapper requestMapper) {
        this.driverMapper = driverMapper;
        this.userDocumentMapper = userDocumentMapper;
        this.documentImageMapper = documentImageMapper;
        this.userMapper = userMapper;
        this.requestMapper = requestMapper;
    }

    @Override
    @Transactional
    public int createDriver(DriverReq driverReq) {
        Long checkValidRequest = checkUserIdValidity(driverReq.getUserId());
        if (checkValidRequest == -1L) {
            throw new InvalidRequestException(
                    "Invalid Request: userId " + driverReq.getUserId() + " is already in the system");
        }
        if (checkValidRequest > 0L) {
            if (updateDriverByUserId(driverReq) == 1
                    && requestMapper.updateRequestStatus(checkValidRequest, 1L) == 1) {
                return 1;
            }
        }
        if (checkValidRequest == 0L) {
            boolean insertDriverSuccess = insertDriver(driverReq);
            boolean insertRegistrationRequestSuccess = insertRegistrationRequest(driverReq.getUserId());
            if (insertDriverSuccess && insertRegistrationRequestSuccess) {
                return 1;
            }
        }
        return 0;
    }

    /*  -1 Invalid: Likely Spam Return
        > 0 Old: Update
        0 Valid: Create New  */
    private Long checkUserIdValidity(String userId) {
        Optional<Request> optionalRequest = requestMapper.findRequestByUserId(userId, 4L);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            if (request.getRequestStatusId() == 3
                    && checkIfAfterOneMonth(request.getRequestDate())) {
                return request.getRequestId();
            } else {
                return -1L;
            }
        }
        return 0L;
    }

    private boolean checkIfAfterOneMonth(LocalDate requestDate) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(today, requestDate);
        return period.getMonths() <= -1;
    }

    private boolean insertDriver(DriverReq driverReq) {
        int documentRecords = 0;
        int documentImageRecords = 0;
        int driverRecord = driverMapper.insertDriver(driverReq);
        for (UserDocumentReq userDocumentReq : driverReq.getUserDocumentReqList()) {
            documentRecords += userDocumentMapper.insertDocument(userDocumentReq, driverReq.getUserId());
            for (DocumentImageReq documentImageReq : userDocumentReq.getDocumentImagesReqList()) {
                documentImageRecords += documentImageMapper
                        .insertDocumentImage(
                                documentImageReq, userDocumentReq.getUserDocumentId());
            }
        }
        int userRoles = userMapper.insertRoleForUserId(driverReq.getUserId(), 3);
        return driverRecord == 1
                && documentRecords > 0
                && documentImageRecords > 0
                && userRoles == 1;
    }

    private boolean insertRegistrationRequest(String userId) {
        Request request = new Request(
                userId,
                1L,
                "New Registration",
                false);
        int requestRecord = requestMapper.insertRequest(request);
        int userRequestRecord = requestMapper.insertUserRequest(request.getRequestId(), 4);
        return requestRecord > 0
                && userRequestRecord > 0;
    }

    @Override
    public DriverDetail findDriverById(String userId) {
        Optional<DriverDetail> optionalDriverDetail = driverMapper.findDriverById(userId);
        optionalDriverDetail.ifPresent(detail ->
                detail.
                        setUserDocumentList(userDocumentMapper.
                                findUserDocumentByUserId(userId)));
        return optionalDriverDetail.orElseThrow(() ->
                new ResourceNotFoundException("Driver with id: " + userId + " not found"));
    }

    @Override
    public List<DriverRes> findDrivers(DriverPageReq driverPageReq) {
        return driverMapper.findDrivers(driverPageReq);
    }

    @Override
    public int findTotalDrivers() {
        return userMapper.findTotalUserByRoles(3);
    }

    @Override
    public int findTotalDriversWhenFiltering(DriverPageReq driverPageReq) {
        return driverMapper.findTotalDriversWhenFilter(driverPageReq);
    }

    @Override
    public int deleteDriverById(String userId) {
        return userMapper.deleteUserById(userId);
    }

    @Transactional
    @Override
    public int updateDriverByUserId(DriverReq driverReq) {
        int userDocumentsUpdated = 0;
        int documentImagesUpdated = 0;
        int driverUpdateSuccess = driverMapper.updateDriver(driverReq);
        for (UserDocumentReq userDocumentReq : driverReq.getUserDocumentReqList()) {
            userDocumentsUpdated += userDocumentMapper.updateDocument(userDocumentReq, driverReq.getUserId());
            for (DocumentImageReq documentImageReq : userDocumentReq.getDocumentImagesReqList()) {
                documentImagesUpdated += documentImageMapper.updateDocumentImage(documentImageReq, userDocumentReq.getUserDocumentId());
            }
        }
        if (driverUpdateSuccess == 1
                && userDocumentsUpdated > 0
                && documentImagesUpdated > 0) {
            return 1;
        }
        return 0;
    }
}
