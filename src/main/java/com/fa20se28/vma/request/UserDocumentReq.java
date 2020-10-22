package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class UserDocumentReq {
    private String userDocumentId;
    private int userDocumentTypeId;
    private String registeredLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date registeredDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiryDate;
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

    public String getRegisteredLocation() {
        return registeredLocation;
    }

    public void setRegisteredLocation(String registeredLocation) {
        this.registeredLocation = registeredLocation;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public List<DocumentImageReq> getDocumentImagesReqList() {
        return documentImagesReqList;
    }

    public void setDocumentImagesReqList(List<DocumentImageReq> documentImagesReqList) {
        this.documentImagesReqList = documentImagesReqList;
    }
}
