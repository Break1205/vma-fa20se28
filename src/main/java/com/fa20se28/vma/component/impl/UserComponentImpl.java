package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.mapper.UserMapper;
import com.fa20se28.vma.request.DocumentImageReq;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.request.UserDocumentReq;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserComponentImpl implements UserComponent {
    private final UserMapper userMapper;


    public UserComponentImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public int createDriver(DriverReq driverReq) {
        int documentRecords = 0;
        int documentImageRecords = 0;
        int driverRecord = userMapper.insertDriver(driverReq);
        for (UserDocumentReq userDocumentReq : driverReq.getUserDocumentReqList()) {
            userDocumentReq.setUserId(driverReq.getUserId());
            userMapper.insertDocument(userDocumentReq);
            documentRecords++;
            for (DocumentImageReq documentImageReq : userDocumentReq.getDocumentImagesReqList()) {
                documentImageReq.setDocumentId(userDocumentReq.getUserDocumentId());
                userMapper.insertDocumentImage(documentImageReq);
                documentImageRecords++;
            }
        }
        if (driverRecord == 1 && documentRecords > 0 && documentImageRecords > 0) {
            return 1;
        }
        return 0;
    }
}
