package com.techticz.dietchart.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class Response {

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}