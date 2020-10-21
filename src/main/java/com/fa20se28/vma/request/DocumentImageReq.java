package com.fa20se28.vma.request;

public class DocumentImageReq {
    private int documentImageId;
    private String imageLink;

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getDocumentImageId() {
        return documentImageId;
    }

    public void setDocumentImageId(int documentImageId) {
        this.documentImageId = documentImageId;
    }
}
