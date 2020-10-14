package com.fa20se28.vma.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UserDocumentReq {
    @Pattern(regexp = "^\\w{9}$|^\\w{12}$|^\\w{15}$")
    private String userDocumentId;
    private int userDocumentTypeId;
    private String userId;
    @NotNull(message = "This field is required")
    private String registerLocation;
    @NotNull(message = "This field is required")
    private String registerDate;
    @NotNull(message = "This field is required")
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
