package com.techticz.app.constant;

/**
 * Created by gssirohi on 5/7/16.
 */
public enum Nutrient {
    CALORIES(1, "Calories"),
    FAT(2, "Fat"),
    CARBS(3, "Carbohydrates"),
    PROTINE(4, "Protine"),
    FIBER(5, "Fiber"),

    POTASSIUM(6, "Potassium"),
    SODIUM(7, "Sodium"),
    CALCIUM(8, "Calcium"),
    IRON(9, "Iron"),

    VITAMIN_A(10, "Vitamin A"),
    VITAMNIN_C(11, "Vitamin C"),

    SUGAR(12, "Sugar"),

    ALCOHOL(13, "Alcohol"),

    CHOLESTRAL(14, "Cholestral");

    private final String name;
    int code;

    private Nutrient(int code, String name) {
        this.name = name;
        this.code = code;
    }

}
