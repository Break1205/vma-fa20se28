package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.RequestComponent;
import com.fa20se28.vma.mapper.RequestMapper;
import com.fa20se28.vma.request.RequestReq;
import org.springframework.stereotype.Component;

@Component
public class RequestComponentImpl implements RequestComponent {
    private final RequestMapper requestMapper;

    public RequestComponentImpl(RequestMapper requestMapper) {
        this.requestMapper = requestMapper;
    }

    @Override
    public int createRequest(RequestReq requestReq, String userDocumentId, String userId) {
        return requestMapper.insertRequest(requestReq, userDocumentId, userId);
    }

}
