package com.fa20se28.vma.model;

import java.util.Date;

public class UserDocumentImageDetail {
    private int userDocumentImageId;
    private int userDocumentId;
    private String imageLink;
    private Date createDate;

    public int getUserDocumentImageId() {
        return userDocumentImageId;
    }

    public void setUserDocumentImageId(int userDocumentImageId) {
        this.userDocumentImageId = userDocumentImageId;
    }

    public int getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(int userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
