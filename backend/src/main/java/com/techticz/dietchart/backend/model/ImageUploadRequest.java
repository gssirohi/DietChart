package com.techticz.dietchart.backend.model;

/**
 * Created by gssirohi on 17/7/17.
 */

public class ImageUploadRequest {
    private String imageData;
    private String imageName;
    private String mimeType;

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
