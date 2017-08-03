package com.techticz.app.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gssirohi on 10/9/16.
 */
public enum Routines {

    EARLY_MORNING(1, "Early Morning", "6 am to 7 am"),
    BREAKFAST(2, "Breakfast", " 8 am to 9 am"),
    LUNCH(3, "Lunch", "11 pm to 12 pm"),
    EVENING_SNACKS(4, "Evening Snack", "3 pm to 4 pm"),

    PRE_DINNER(5, "Pre Dinner", "7 pm to 8 pm"),
    DINNER(6, "Dinner", "9 pm to 10 pm"),
    BED_TIME(7, "Bed Time", "11 pm to 12 pm");

    public final int code;
    public final String lable;
    public final String desc;

    Routines(int i, String lable, String desc) {
        this.code = i;
        this.lable = lable;
        this.desc = desc;
    }


    public static Integer getIdByName(String s) {
        if (s.equalsIgnoreCase(EARLY_MORNING.lable)) return EARLY_MORNING.code;
        if (s.equalsIgnoreCase(BREAKFAST.lable)) return BREAKFAST.code;
        if (s.equalsIgnoreCase(LUNCH.lable)) return LUNCH.code;
        if (s.equalsIgnoreCase(EVENING_SNACKS.lable)) return EVENING_SNACKS.code;
        if (s.equalsIgnoreCase(PRE_DINNER.lable)) return PRE_DINNER.code;
        if (s.equalsIgnoreCase(DINNER.lable)) return DINNER.code;
        if (s.equalsIgnoreCase(BED_TIME.lable)) return BED_TIME.code;
        return 0;
    }

    public static List<Routines> getAll() {
        List<Routines> all = new ArrayList<>();
        all.add(EARLY_MORNING);
        all.add(BREAKFAST);
        all.add(LUNCH);
        all.add(EVENING_SNACKS);
        all.add(PRE_DINNER);
        all.add(DINNER);
        all.add(BED_TIME);
        return all;
    }

    public static List<String> getAllNames() {
        List<Routines> list = getAll();
        List<String> names = new ArrayList<>();
        for (Routines type : list) {
            names.add(type.lable);
        }
        return names;
    }

    public static Routines getById(Integer code) {
        List<Routines> list = getAll();
        for(Routines r : list){
            if(r.code == code){
                return r;
            }
        }
        return null;
    }
}
