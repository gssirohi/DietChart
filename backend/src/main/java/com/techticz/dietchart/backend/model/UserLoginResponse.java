package com.techticz.dietchart.backend.model;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 17/9/17.
 */

public class UserLoginResponse {
    SyncInfoResponse syncInfoResponse;
    String welcomeMsg;
    MarketResponse market;
    MarketResponse myMarket;

    public SyncInfoResponse getSyncInfoResponse() {
        return syncInfoResponse;
    }

    public void setSyncInfoResponse(SyncInfoResponse syncInfoResponse) {
        this.syncInfoResponse = syncInfoResponse;
    }

    public String getWelcomeMsg() {
        return welcomeMsg;
    }

    public void setWelcomeMsg(String welcomeMsg) {
        this.welcomeMsg = welcomeMsg;
    }

    public MarketResponse getMarket() {
        return market;
    }

    public void setMarket(MarketResponse market) {
        this.market = market;
    }

    public MarketResponse getMyMarket() {
        return myMarket;
    }

    public void setMyMarket(MarketResponse myMarket) {
        this.myMarket = myMarket;
    }
}
