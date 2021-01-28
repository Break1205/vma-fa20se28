package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserDocumentComponent;
import com.fa20se28.vma.configuration.exception.DataExecutionException;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.enums.DocumentStatus;
import com.fa20se28.vma.mapper.UserDocumentImageMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.UserDocumentDetail;
import com.fa20se28.vma.request.UserDocumentImageReq;
import com.fa20se28.vma.request.UserDocumentReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class UserDocumentComponentImpl implements UserDocumentComponent {
    private final UserDocumentMapper userDocumentMapper;
    private final UserDocumentImageMapper userDocumentImageMapper;

    public UserDocumentComponentImpl(UserDocumentMapper userDocumentMapper,
                                     UserDocumentImageMapper userDocumentImageMapper) {
        this.userDocumentMapper = userDocumentMapper;
        this.userDocumentImageMapper = userDocumentImageMapper;
    }

    @Override
    public List<UserDocument> findUserDocumentByUserId(String userId, DocumentStatus documentStatus) {
        return userDocumentMapper.findUserDocumentByUserId(userId, documentStatus);
    }

    /*
    ADMIN
    * */
    @Transactional
    @Override
    public int createUserDocument(UserDocumentReq userDocumentReq, String userId, DocumentStatus documentStatus) {
        List<UserDocument> currentUserDocuments = userDocumentMapper.getCurrentUserDocuments(userId, DocumentStatus.VALID);
        for (UserDocument userDocument : currentUserDocuments) {
            if (userDocumentReq.getUserDocumentType().equals(userDocument.getUserDocumentType())) {
                throw new DataExecutionException("User with id: " + userId + " had already has " + userDocument.getUserDocumentType());
            }
        }
        Optional<UserDocumentDetail> optionalUserDocumentDetail = userDocumentMapper
                .findUserDocumentDetailByUserDocumentNumber(userDocumentReq.getUserDocumentNumber(), DocumentStatus.VALID);
        if (optionalUserDocumentDetail.isPresent()) {
            throw new DataExecutionException("Document with number: " + userDocumentReq.getUserDocumentNumber() + " is duplicated");
        }
        int userDocumentImageRecords = 0;
        int userDocumentRecords = userDocumentMapper.insertDocument(userDocumentReq, userId, documentStatus);
        if (userDocumentRecords == 0) {
            throw new DataExecutionException("Can not insert User Document Record");
        }
        for (UserDocumentImageReq userDocumentImageReq : userDocumentReq.getUserDocumentImages()) {
            userDocumentImageRecords += userDocumentImageMapper
                    .insertUserDocumentImage(
                            userDocumentImageReq, userDocumentReq.getUserDocumentId());
        }
        if (userDocumentImageRecords >= 0) {
            return 1;
        }
        throw new DataExecutionException("Can not insert User Document Image Record");
    }

    @Transactional
    @Override
    public int updateUserDocument(UserDocumentReq userDocumentReq, String userId) {
        UserDocumentDetail userDocumentDetail = findUserDocumentDetailById(userDocumentReq.getUserDocumentId(), DocumentStatus.VALID);
        if (userDocumentDetail == null) {
            throw new ResourceNotFoundException("Can not find User Document with number: " + userDocumentReq.getUserDocumentNumber());
        }
        if (!userId.equals(userDocumentDetail.getUserId())) {
            throw new DataExecutionException("This Document does not belong to User with id: " + userId);
        }

        int userDocumentRecords = userDocumentMapper.updateDocument(userDocumentReq, userDocumentDetail.getUserDocumentId(), userId);
        if (userDocumentRecords == 0) {
            throw new DataExecutionException("Can not update User Document Record");
        }

        userDocumentImageMapper.deleteUserDocumentImage(String.valueOf(userDocumentDetail.getUserDocumentId()));
        int userDocumentImageRecords = 0;
        for (UserDocumentImageReq userDocumentImageReq : userDocumentReq.getUserDocumentImages()) {
            userDocumentImageRecords += userDocumentImageMapper
                    .insertUserDocumentImage(
                            userDocumentImageReq, userDocumentDetail.getUserDocumentId());
        }
        if (userDocumentImageRecords >= 0) {
            return 1;
        }
        throw new DataExecutionException("Can not update User Document Image Record");

    }

    @Transactional
    @Override
    public void deleteUserDocument(int userDocumentId) {
        UserDocumentDetail userDocumentDetail = findUserDocumentDetailById(userDocumentId, DocumentStatus.VALID);
        if (userDocumentDetail == null) {
            throw new ResourceNotFoundException("Can not find User Document with id: " + userDocumentId);
        }
        userDocumentMapper.deleteUserDocument(String.valueOf(userDocumentDetail.getUserDocumentId()));
        userDocumentImageMapper.deleteUserDocumentImage(String.valueOf(userDocumentDetail.getUserDocumentId()));
    }

    @Override
    public void deleteUserDocumentWithRequest(String userDocumentId) {
        userDocumentMapper.deleteUserDocument(userDocumentId);
        userDocumentImageMapper.deleteUserDocumentImage(userDocumentId);
    }

    @Override
    public int createUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId, DocumentStatus documentStatus) {
        Optional<UserDocumentDetail> optionalUserDocumentDetail = userDocumentMapper
                .findUserDocumentDetailByUserDocumentNumber(userDocumentReq.getUserDocumentNumber(), DocumentStatus.VALID);
        if (optionalUserDocumentDetail.isPresent()) {
            throw new DataExecutionException("Document with number: " + userDocumentReq.getUserDocumentNumber() + " is duplicated");
        }
        List<UserDocument> currentUserDocuments = userDocumentMapper.getCurrentUserDocuments(userId, DocumentStatus.PENDING);
        for (UserDocument userDocument : currentUserDocuments) {
            if (userDocument.getUserDocumentType().equals(userDocumentReq.getUserDocumentType())) {
                throw new DataExecutionException("There is already a PENDING User Document of this type: " +
                        userDocumentReq.getUserDocumentType() + ". Waiting to be handle");
            }
        }
        int userDocumentImageRecords = 0;
        int userDocumentRecords = userDocumentMapper.insertDocument(userDocumentReq, userId, documentStatus);
        if (userDocumentRecords == 0) {
            throw new DataExecutionException("Can not insert User Document Record");
        }
        for (UserDocumentImageReq userDocumentImageReq : userDocumentReq.getUserDocumentImages()) {
            userDocumentImageRecords += userDocumentImageMapper
                    .insertUserDocumentImage(
                            userDocumentImageReq, userDocumentReq.getUserDocumentId());
        }
        if (userDocumentImageRecords >= 0) {
            return 1;
        }
        throw new DataExecutionException("Can not insert User Document Image Record");
    }

    @Override
    public int createUpdateUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId, DocumentStatus documentStatus) {
        List<UserDocument> currentUserDocuments = userDocumentMapper.getCurrentUserDocuments(userId, DocumentStatus.PENDING);
        for (UserDocument userDocument : currentUserDocuments) {
            if (userDocument.getUserDocumentType().equals(userDocumentReq.getUserDocumentType())) {
                throw new DataExecutionException("There is already a PENDING User Document of this type: " +
                        userDocumentReq.getUserDocumentType() + ". Waiting to be handle");
            }
        }
        int userDocumentImageRecords = 0;
        int userDocumentRecords = userDocumentMapper.insertDocument(userDocumentReq, userId, documentStatus);
        if (userDocumentRecords == 0) {
            throw new DataExecutionException("Can not insert User Document Record");
        }
        for (UserDocumentImageReq userDocumentImageReq : userDocumentReq.getUserDocumentImages()) {
            userDocumentImageRecords += userDocumentImageMapper
                    .insertUserDocumentImage(
                            userDocumentImageReq, userDocumentReq.getUserDocumentId());
        }
        if (userDocumentImageRecords >= 0) {
            return 1;
        }
        throw new DataExecutionException("Can not insert User Document Image Record");
    }

    @Override
    public int acceptNewDocumentRequest(String userDocumentId) {
        UserDocumentDetail userDocumentDetail = userDocumentMapper.findUserDocumentByUserDocumentId(userDocumentId);
        if (userDocumentDetail.getUserId() != null) {
            List<UserDocument> currentUserDocuments = userDocumentMapper.getCurrentUserDocuments(userDocumentDetail.getUserId(), DocumentStatus.VALID);
            for (UserDocument userDocument : currentUserDocuments) {
                if (userDocumentDetail.getUserDocumentType().equals(userDocument.getUserDocumentType())) {
                    int success = userDocumentMapper.updateStatus(userDocument.getUserDocumentId(), DocumentStatus.OUTDATED);
                    if (success != 1) {
                        throw new DataExecutionException("Can not update status of user document with id: " + userDocument.getUserDocumentId());
                    }
                }
            }
            return userDocumentMapper.updateStatus(userDocumentId, DocumentStatus.VALID);
        }
        return 0;
    }

    @Override
    public int denyUpdateDocumentRequest(String userDocumentId) {
        int success = userDocumentMapper.updateStatus(userDocumentId, DocumentStatus.REJECTED);
        if (success != 1) {
            throw new DataExecutionException("Can not update status of user document with id: " + userDocumentId);
        }
        return success;
    }

    @Override
    public UserDocumentDetail findUserDocumentDetailById(int userDocumentId, DocumentStatus documentStatus) {
        Optional<UserDocumentDetail> optionalUserDocumentDetail = userDocumentMapper.findUserDocumentDetail(userDocumentId, documentStatus);
        optionalUserDocumentDetail.ifPresent(detail ->
                detail.setUserDocumentImages(
                        userDocumentImageMapper.findUserDocumentImageDetail(optionalUserDocumentDetail.get().getUserDocumentId())));
        return optionalUserDocumentDetail.orElseThrow(() -> new ResourceNotFoundException("User document with number: " + userDocumentId + " not found"));
    }
}