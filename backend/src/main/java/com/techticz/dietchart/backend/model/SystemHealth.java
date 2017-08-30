package com.techticz.dietchart.backend.model;

/**
 * Created by gssirohi on 15/7/17.
 */
public class SystemHealth {
    private String status = "active";
    private String detail = "very active";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

