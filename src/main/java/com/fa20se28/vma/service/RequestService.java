package com.fa20se28.vma.service;

import com.fa20se28.vma.request.RequestReq;

public interface RequestService {
    int createNewDocumentRequest(RequestReq requestReq);

    int createUpdateDocumentRequest(RequestReq requestReq);

    int createDeleteDocumentRequest(RequestReq requestReq);
}
