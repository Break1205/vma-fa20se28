package com.fa20se28.vma.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class UserDocumentReq {
    private String userDocumentId;
    private int userDocumentTypeId;
    private String registerLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date registerDate;
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

    public String getRegisterLocation() {
        return registerLocation;
    }

    public void setRegisterLocation(String registerLocation) {
        this.registerLocation = registerLocation;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
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
