package com.techticz.app.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gssirohi on 10/9/16.
 */
public enum Units {

    MILLI(1, "milli"),
    GRAM(2, "gram");

    private final int code;
    public final String lable;

    Units(int i, String lable) {
        this.code = i;
        this.lable = lable;
    }


    public static Integer getIdByName(String s) {
        if (s.equalsIgnoreCase(MILLI.lable)) return MILLI.code;
        if (s.equalsIgnoreCase(GRAM.lable)) return GRAM.code;
        return 0;
    }

    public static List<Units> getAll() {
        List<Units> all = new ArrayList<>();
        all.add(MILLI);
        all.add(GRAM);
        return all;
    }

    public static List<String> getAllNames() {
        List<Units> list = getAll();
        List<String> names = new ArrayList<>();
        for (Units type : list) {
            names.add(type.lable);
        }
        return names;
    }

    public static Units getById(int type) {
        if (type == MILLI.code) return MILLI;
        if (type == GRAM.code) return GRAM;
        return MILLI;
    }
}
