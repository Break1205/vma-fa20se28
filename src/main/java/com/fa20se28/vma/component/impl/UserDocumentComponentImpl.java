package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.DocumentComponent;
import com.fa20se28.vma.mapper.UserDocumentImageMapper;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.request.UserDocumentImageReq;
import com.fa20se28.vma.request.UserDocumentReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserDocumentComponentImpl implements DocumentComponent {
    private final UserDocumentMapper userDocumentMapper;
    private final UserDocumentImageMapper userDocumentImageMapper;

    public UserDocumentComponentImpl(UserDocumentMapper userDocumentMapper,
                                     UserDocumentImageMapper userDocumentImageMapper) {
        this.userDocumentMapper = userDocumentMapper;
        this.userDocumentImageMapper = userDocumentImageMapper;
    }

    @Override
    public List<UserDocument> findUserDocumentByUserId(String id) {
        return userDocumentMapper.findUserDocumentByUserId(id);
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
}
