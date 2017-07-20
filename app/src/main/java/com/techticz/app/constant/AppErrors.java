package com.techticz.app.constant;

/**
 * Created by gssirohi on 5/7/16.
 */
public enum AppErrors {
    TRIP_NULL(0, "TRIP_NUll", "Couldn't fetch trip from response"),
    RESPONSE_NULL(1, "RESPONSE_NUll", "No response from server"),
    TRIP_CREATE_ERROR(2, "TRIP_CREATE_ERROR", "No response from server"),
    PRODUCT_LIST_NULL(3, "PRODUCT_LIST_NULL", "Could not retrive list from response");


    String name;
    int code;
    String message;

    private AppErrors(int code, String name, String message) {
        this.name = name;
        this.code = code;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TripError:[" + name + "][" + message + "]";
    }
}
