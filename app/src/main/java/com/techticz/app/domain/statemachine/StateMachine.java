package com.techticz.app.domain.statemachine;

/**
 * Created by gssirohi on 8/7/16.
 */
public abstract class StateMachine {

    public static final String STATE_ACTION_SUBMIT_TRIP = "STATE_ACTION_SUBMIT_TRIP";
    public static final String STATE_ACTION_DONE_TRIP = "STATE_ACTION_DONE_TRIP";
    public static final String STATE_ACTION_PAYMENT_SUCCESS = "STATE_ACTION_PAYMENT_SUCCESS";
    public static final String STATE_ACTION_REQUEST_PRODUCT = "STATE_ACTION_REQUEST_PRODUCT";
    public static final String STATE_ACTION_ADD_TO_CART_NO_PAYMENT = "STATE_ACTION_ADD_TO_CART_NO_PAYMENT";
    public static final String STATE_ACTION_APPROVAL_SUCCESS = "STATE_ACTION_APPROVAL_SUCCESS";
    public static final String STATE_ACTION_APPROVAL_DECLINED = "STATE_ACTION_APPROVAL_DECLINED";
    public static final String STATE_ACTION_PAYMENT_FAILED = "STATE_ACTION_PAYMENT_FAILED";
    public static final String STATE_ACTION_PRODUCT_EXPIRED = "STATE_ACTION_PRODUCT_EXPIRED";
    public static final String STATE_ACTION_OFFLINE_BOOKING_FAILED = "STATE_ACTION_OFFLINE_BOOKING_FAILED";
    public static final String STATE_ACTION_OFFLINE_BOOKING_SUCCESS = "STATE_ACTION_OFFLINE_BOOKING_SUCCESS";

    public static final String STATE_ACTION_ALL_PRODUCT_BOOKED = "STATE_ACTION_ALL_PRODUCT_BOOKED";
    public static final String STATE_ACTION_ALL_PRODUCT_EXPIRED = "STATE_ACTION_ALL_PRODUCT_EXPIRED";

    String lastModifyingAction = "";


    public String getLastModifyingAction() {
        return lastModifyingAction;
    }

    public void setLastModifyingAction(String lastModifyingAction) {
        this.lastModifyingAction = lastModifyingAction;
    }
}
