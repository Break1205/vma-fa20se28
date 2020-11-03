package com.fa20se28.vma.component;

import com.fa20se28.vma.request.RequestReq;

public interface RequestComponent {
    int createRequest(RequestReq requestReq, String userDocumentId, String userId);
}
