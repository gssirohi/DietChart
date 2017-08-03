package com.techticz.app.constant;

/**
 * Created by gssirohi on 5/7/16.
 */
public enum LaunchMode {

CREATE(0),VIEW(1),EDIT(2);
    public int code;
    private LaunchMode(int code) {
        this.code = code;
    }

    public static LaunchMode getById(int mode) {
        if(mode == 0) return CREATE;
        if(mode == 1) return VIEW;
        if(mode == 2) return EDIT;
        return VIEW;
    }
}
