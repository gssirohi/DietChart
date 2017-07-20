package com.techticz.app.constant;

/**
 * Created by gssirohi on 5/7/16.
 */
public enum Repositories {
    MOCK(0),
    API(1),
    DATABASE(2),
    CACHE(3);


    int code;

    private Repositories(int code) {
        this.code = code;
    }

}
