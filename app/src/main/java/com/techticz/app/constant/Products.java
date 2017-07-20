package com.techticz.app.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gssirohi on 10/9/16.
 */
public enum Products {

    COMIC(0, "COMICS"), CHARACTER(1, "CHARACTERS"), EVENT(2, "EVENTS"), SERIES(3, "SERIES"), STORY(4, "STORIES"), CREATOR(5, "CREATORS");

    private final int code;
    private final String lable;

    Products(int i, String lable) {
        this.code = i;
        this.lable = lable;
    }

    public int getCode() {
        return code;
    }

    public String getLable() {
        return lable;
    }

    public static List<Products> getList() {
        ArrayList<Products> list = new ArrayList<>();
        list.add(COMIC);
        list.add(CHARACTER);
        list.add(EVENT);
        list.add(SERIES);
        list.add(STORY);
        list.add(CREATOR);
        return list;
    }
}
