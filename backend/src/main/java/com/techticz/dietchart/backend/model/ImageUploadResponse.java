package com.techticz.dietchart.backend.model;

/**
 * Created by gssirohi on 17/7/17.
 */

public class ImageUploadResponse {
    private String blobKey;
    private String servingUrl;

    public String getBlobKey() {
        return blobKey;
    }

    public void setBlobKey(String blobKey) {
        this.blobKey = blobKey;
    }

    public String getServingUrl() {
        return servingUrl;
    }

    public void setServingUrl(String servingUrl) {
        this.servingUrl = servingUrl;
    }
}
