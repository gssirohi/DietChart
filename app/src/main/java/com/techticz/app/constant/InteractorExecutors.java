package com.techticz.app.constant;

/**
 * Created by gssirohi on 5/7/16.
 */
public enum InteractorExecutors {
    ASYNC_TASK(0),
    THREAD(1);


    int code;

    private InteractorExecutors(int code) {
        this.code = code;
    }

}
