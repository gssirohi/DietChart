package com.techticz.dietchart.backend;

/**
 * Created by gssirohi on 17/7/17.
 */

public class ImageDownloadResponse {
    private String blobKey;
    private String servingUrl;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
