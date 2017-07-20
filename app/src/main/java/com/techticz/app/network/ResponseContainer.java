package com.techticz.app.network;

import com.google.gson.annotations.SerializedName;

public class ResponseContainer implements Responsible {

    @SerializedName("resCode")
    protected int resCode;
    @SerializedName("resMessage")
    protected String resMessage;
    @SerializedName("interactionId")
    protected String interactionId;
    @SerializedName("sessionId")
    protected String sessionId;
    @SerializedName("interationType")
    protected String interactionType;
    @SerializedName("action")
    protected String action;
    protected RequestCodes requestCode;
    protected Request currentRequest;

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(Request currentRequest) {
        this.currentRequest = currentRequest;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

    public String getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;

    }

    public String getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    @Override
    public void setRequestCode(RequestCodes code) {

        this.requestCode = code;
    }

    @Override
    public RequestCodes getRequestCode() {

        return requestCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "ResponseContainer [resCode=" + resCode + ", resMessage=" + resMessage + ", interactionId="
                + interactionId + ", sessionId=" + sessionId + ", interactionType=" + interactionType + ", action="
                + action + ", requestCode=" + requestCode + "]";
    }
}
