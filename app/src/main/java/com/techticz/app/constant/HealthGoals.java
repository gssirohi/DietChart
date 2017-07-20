package com.techticz.app.constant;

import com.techticz.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gssirohi on 10/9/16.
 */
public enum HealthGoals {

    WEIGHT_GAIN(1, "Weight Gain", "Healthy weight gain", R.drawable.bg_card_2),
    WEIGHT_MAINTAIN(2, "Maintain Weight", "Maintain healthy weight gain", R.drawable.bg_card_4),
    WEIGHT_LOSS(3, "Loss Weight", "Loss Weight and gain muscels", R.drawable.bg_card_5);

    public final int code;
    public final String lable;
    public final String desc;
    public final int res;

    HealthGoals(int i, String lable, String desc, int res) {
        this.code = i;
        this.lable = lable;
        this.desc = desc;
        this.res = res;
    }


    public static Integer getIdByName(String s) {
        if (s.equalsIgnoreCase(WEIGHT_GAIN.lable)) return WEIGHT_GAIN.code;
        if (s.equalsIgnoreCase(WEIGHT_MAINTAIN.lable)) return WEIGHT_MAINTAIN.code;
        if (s.equalsIgnoreCase(WEIGHT_LOSS.lable)) return WEIGHT_LOSS.code;
        return 0;
    }

    public static List<HealthGoals> getAll() {
        List<HealthGoals> all = new ArrayList<>();
        all.add(WEIGHT_GAIN);
        all.add(WEIGHT_MAINTAIN);
        all.add(WEIGHT_LOSS);
        return all;
    }

    public static List<String> getAllNames() {
        List<HealthGoals> list = getAll();
        List<String> names = new ArrayList<>();
        for (HealthGoals type : list) {
            names.add(type.lable);
        }
        return names;
    }

    public static HealthGoals getById(int type) {
        if (type == WEIGHT_GAIN.code) return WEIGHT_GAIN;
        if (type == WEIGHT_MAINTAIN.code) return WEIGHT_MAINTAIN;
        if (type == WEIGHT_LOSS.code) return WEIGHT_LOSS;
        return WEIGHT_GAIN;
    }
}
