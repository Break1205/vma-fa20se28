package com.fa20se28.vma.request;

import java.util.List;

public class UserDocumentReq {
    private String userDocumentId;
    private int userDocumentTypeId;
    private String userId;
    private String registerLocation;
    private String registerDate;
    private String expiryDate;
    private String otherInformation;
    private List<DocumentImageReq> documentImagesReqList;

    public String getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(String userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public int getUserDocumentTypeId() {
        return userDocumentTypeId;
    }

    public void setUserDocumentTypeId(int userDocumentTypeId) {
        this.userDocumentTypeId = userDocumentTypeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegisterLocation() {
        return registerLocation;
    }

    public void setRegisterLocation(String registerLocation) {
        this.registerLocation = registerLocation;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<DocumentImageReq> getDocumentImagesReqList() {
        return documentImagesReqList;
    }

    public void setDocumentImagesReqList(List<DocumentImageReq> documentImagesReqList) {
        this.documentImagesReqList = documentImagesReqList;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }
}
