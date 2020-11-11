package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserDocumentComponent;
import com.fa20se28.vma.configuration.exception.ResourceNotFoundException;
import com.fa20se28.vma.mapper.UserDocumentImageMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.UserDocumentDetail;
import com.fa20se28.vma.model.UserDocumentImageDetail;
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
    public List<UserDocument> findUserDocumentByUserId(String id, int option) {
        return userDocumentMapper.findUserDocumentByUserId(id, option);
    }

    /*
    ADMIN
    * */
    @Transactional
    @Override
    public int createUserDocument(UserDocumentReq userDocumentReq, String userId) {
        int userDocumentImageLogRecords = 0;
        int userDocumentRecords = userDocumentMapper.insertDocument(userDocumentReq, userId);
        int userDocumentLogRecords = userDocumentMapper.insertDocumentLog(userDocumentReq, userId);
        for (UserDocumentImageReq userDocumentImageReq : userDocumentReq.getUserDocumentImages()) {
            userDocumentImageMapper
                    .insertUserDocumentImage(
                            userDocumentImageReq, userDocumentReq.getUserDocumentId());
            userDocumentImageLogRecords += userDocumentImageMapper
                    .insertUserDocumentImageLog(
                            userDocumentImageReq, userDocumentReq.getUserDocumentId());
        }
        if (userDocumentRecords == 1
                && userDocumentLogRecords == 1
                && userDocumentImageLogRecords >= 0) {
            return 1;
        }
        return 0;
    }

    @Transactional
    @Override
    public int updateUserDocument(UserDocumentReq userDocumentReq, String userId) {
        int userDocumentImageLogRecords = 0;
        int userDocumentRecords = userDocumentMapper.updateDocument(userDocumentReq, userId);
        int userDocumentLogRecords = userDocumentMapper.insertDocumentLog(userDocumentReq, userId);
        for (UserDocumentImageReq userDocumentImageReq : userDocumentReq.getUserDocumentImages()) {
            userDocumentImageMapper.updateUserDocumentImage(userDocumentImageReq, userDocumentReq.getUserDocumentId());
            userDocumentImageLogRecords += userDocumentImageMapper.insertUserDocumentImageLog(
                    userDocumentImageReq, userDocumentReq.getUserDocumentId());
        }
        if (userDocumentRecords == 1
                && userDocumentLogRecords == 1
                && userDocumentImageLogRecords >= 0) {
            return 1;
        }
        return 0;
    }

    @Transactional
    @Override
    public void deleteUserDocument(String userDocumentId) {
        userDocumentMapper.deleteUserDocument(userDocumentId);
        userDocumentImageMapper.deleteUserDocumentImage(userDocumentId);
    }

    @Transactional
    @Override
    public int createUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId) {
        int userDocumentImageRecords = 0;
        int userDocumentRecords = userDocumentMapper.insertDocument(userDocumentReq, userId);
        for (UserDocumentImageReq userDocumentImageReq : userDocumentReq.getUserDocumentImages()) {
            userDocumentImageRecords += userDocumentImageMapper.insertUserDocumentImage(
                    userDocumentImageReq, userDocumentReq.getUserDocumentId());
        }
        if (userDocumentRecords == 1
                && userDocumentImageRecords >= 0) {
            return 1;
        }
        return 0;
    }


    @Transactional
    @Override
    public int updateUserDocumentWithRequest(UserDocumentReq userDocumentReq, String userId) {
        int userDocumentImageRecords = 0;
        int userDocumentRecords = userDocumentMapper.updateDocument(userDocumentReq, userId);
        for (UserDocumentImageReq userDocumentImageReq : userDocumentReq.getUserDocumentImages()) {
            userDocumentImageRecords += userDocumentImageMapper.updateUserDocumentImage(
                    userDocumentImageReq, userDocumentReq.getUserDocumentId());
        }
        if (userDocumentRecords == 1
                && userDocumentImageRecords >= 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int acceptNewDocumentRequest(String userDocumentId) {
        Optional<UserDocumentDetail> optionalUserDocumentDetail =
                userDocumentMapper.findUserDocumentDetail(userDocumentId);
        optionalUserDocumentDetail.ifPresent(detail ->
                detail.setUserDocumentImages(
                        userDocumentImageMapper.findUserDocumentImageDetail(userDocumentId)));
        if (optionalUserDocumentDetail.isPresent()) {
            int userDocumentLog = userDocumentMapper
                    .acceptNewUserDocumentLog(optionalUserDocumentDetail.get());
            int userDocumentImageLog = 0;
            for (UserDocumentImageDetail imageDetail : optionalUserDocumentDetail.get().getUserDocumentImages()) {
                userDocumentImageLog += userDocumentImageMapper
                        .acceptNewUserDocumentImageLog(imageDetail);
            }
            if (userDocumentLog == 1
                    && userDocumentImageLog >= 0) {
                return 1;
            }
            return 0;
        }
        return 0;
    }

    @Override
    public int denyUpdateDocumentRequest(String userDocumentId) {
        Optional<UserDocumentDetail> optionalUserDocumentDetail =
                userDocumentMapper.findUserDocumentDetailFromLog(userDocumentId);
        optionalUserDocumentDetail.ifPresent(detail ->
                detail.setUserDocumentImages(
                        userDocumentImageMapper.findUserDocumentImageDetailLog(userDocumentId)));
        if (optionalUserDocumentDetail.isPresent()) {
            int userDocumentLog = userDocumentMapper
                    .denyUpdateUserDocument(optionalUserDocumentDetail.get());
            int userDocumentImageLog = 0;
            for (UserDocumentImageDetail imageDetail : optionalUserDocumentDetail.get().getUserDocumentImages()) {
                userDocumentImageLog += userDocumentImageMapper
                        .denyNewUserDocumentImageLog(imageDetail);
            }
            if (userDocumentLog == 1
                    && userDocumentImageLog >= 0) {
                return 1;
            }
            return 0;
        }
        return 0;
    }

    @Override
    public UserDocumentDetail findUserDocumentDetailById(String userDocumentId) {
        Optional<UserDocumentDetail> optionalUserDocumentDetail = userDocumentMapper.findUserDocumentDetail(userDocumentId);
        optionalUserDocumentDetail.ifPresent(detail ->
                detail.setUserDocumentImages(
                        userDocumentImageMapper.findUserDocumentImageDetail(userDocumentId)));
        return optionalUserDocumentDetail.orElseThrow(() -> new ResourceNotFoundException("User document with id: " + userDocumentId + " not found"));
    }
}

