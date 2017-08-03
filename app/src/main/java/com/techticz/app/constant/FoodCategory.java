package com.techticz.app.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by gssirohi on 10/9/16.
 */
public enum FoodCategory {

    MILK(1, "Milk"),
    BREAD(2, "Bread"),
    FRUIT(3, "Fruit"),
    DRY_FRUIT(4, "Dry Fruit"),
    VEGITABLE(5, "Vegitable"),
    DESERT(6, "Desert"),
    CHICKEN(7, "Chicken"),
    MEAT(8, "Meat"),
    SHAKE(9, "Shake"),
    BUTTER(10, "Butter"),
    BURGER(11, "Burger"),
    SANDWICH(12, "Sandwich"),
    PIZZA(13, "Pizza"),
    OTHER(14, "Other");

    public final int code;
    public final String lable;

    FoodCategory(int i, String lable) {
        this.code = i;
        this.lable = lable;
    }


    public static Integer getIdByName(String s) {
        if (s.equalsIgnoreCase(MILK.lable)) return MILK.code;
        if (s.equalsIgnoreCase(BREAD.lable)) return BREAD.code;
        if (s.equalsIgnoreCase(FRUIT.lable)) return FRUIT.code;
        if (s.equalsIgnoreCase(DRY_FRUIT.lable)) return DRY_FRUIT.code;
        if (s.equalsIgnoreCase(VEGITABLE.lable)) return VEGITABLE.code;
        if (s.equalsIgnoreCase(DESERT.lable)) return DESERT.code;
        if (s.equalsIgnoreCase(MEAT.lable)) return MEAT.code;
        if (s.equalsIgnoreCase(CHICKEN.lable)) return CHICKEN.code;
        if (s.equalsIgnoreCase(SHAKE.lable)) return SHAKE.code;
        if (s.equalsIgnoreCase(BUTTER.lable)) return BUTTER.code;
        if (s.equalsIgnoreCase(BURGER.lable)) return BURGER.code;
        if (s.equalsIgnoreCase(SANDWICH.lable)) return SANDWICH.code;
        if (s.equalsIgnoreCase(PIZZA.lable)) return PIZZA.code;
        if (s.equalsIgnoreCase(OTHER.lable)) return OTHER.code;

        return 0;
    }

    public static List<FoodCategory> getAll() {
        List<FoodCategory> all = new ArrayList<>();
        all.add(MILK);
        all.add(BREAD);
        all.add(FRUIT);
        all.add(DRY_FRUIT);
        all.add(VEGITABLE);
        all.add(DESERT);
        all.add(MEAT);
        all.add(CHICKEN);
        all.add(SHAKE);
        all.add(BUTTER);
        all.add(BURGER);
        all.add(SANDWICH);
        all.add(PIZZA);
        all.add(OTHER);
        return all;
    }

    public static List<String> getAllNames() {
        List<FoodCategory> list = getAll();
        List<String> names = new ArrayList<>();
        for (FoodCategory type : list) {
            names.add(type.lable);
        }
        return names;
    }

    public static FoodCategory getById(int type) {
        List<FoodCategory> list = getAll();
        for(FoodCategory cat: list){
            if(type == cat.code) return cat;
        }
        return MILK;
    }
}
