package com.techticz.dietchart.backend.model;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 17/9/17.
 */

public class SyncInfoResponse {
    boolean isLoggedIn;
    boolean isForceLogout;
    String forceLogoutMsg;
    UpdateInfo updateInfo;
    Authorities authorities;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isForceLogout() {
        return isForceLogout;
    }

    public void setForceLogout(boolean forceLogout) {
        isForceLogout = forceLogout;
    }

    public String getForceLogoutMsg() {
        return forceLogoutMsg;
    }

    public void setForceLogoutMsg(String forceLogoutMsg) {
        this.forceLogoutMsg = forceLogoutMsg;
    }

    public UpdateInfo getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(UpdateInfo updateInfo) {
        this.updateInfo = updateInfo;
    }

    public Authorities getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Authorities authorities) {
        this.authorities = authorities;
    }
}
