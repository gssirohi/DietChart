package com.techticz.app.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gssirohi on 10/9/16.
 */
public enum FoodType {

    VEG(1, "Vegiterian"), NON_VEG(2, "Non-Vegiterian");

    private final int code;
    public final String lable;

    FoodType(int i, String lable) {
        this.code = i;
        this.lable = lable;
    }


    public static Integer getIdByName(String s) {
        if (s.equalsIgnoreCase(VEG.lable)) return VEG.code;
        if (s.equalsIgnoreCase(NON_VEG.lable)) return NON_VEG.code;
        return 0;
    }

    public static List<FoodType> getAll() {
        List<FoodType> all = new ArrayList<>();
        all.add(VEG);
        all.add(NON_VEG);
        return all;
    }

    public static List<String> getAllNames() {
        List<FoodType> list = getAll();
        List<String> names = new ArrayList<>();
        for (FoodType type : list) {
            names.add(type.lable);
        }
        return names;
    }

    public static FoodType getById(int type) {
        if (type == VEG.code) return VEG;
        if (type == NON_VEG.code) return NON_VEG;
        return VEG;
    }
}
