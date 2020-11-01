package com.fa20se28.vma.request;

public class UserDocumentImageReq {
    private int userDocumentImageId;
    private String imageLink;

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getUserDocumentImageId() {
        return userDocumentImageId;
    }

    public void setUserDocumentImageId(int userDocumentImageId) {
        this.userDocumentImageId = userDocumentImageId;
    }
}
