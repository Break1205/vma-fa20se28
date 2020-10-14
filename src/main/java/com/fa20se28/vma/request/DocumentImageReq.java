package com.fa20se28.vma.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DocumentImageReq {
    @Pattern(regexp = "^\\w{9}$|^\\w{12}$|^\\w{15}$")
    private String documentId;
    @NotNull(message = "This field is required")
    private String imageLink;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
