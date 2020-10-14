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




}
