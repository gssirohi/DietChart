package com.techticz.app.constant;

/**
 * Created by gssirohi on 5/7/16.
 */
public enum UseCases {
    FETCH_DAY_MEALS("FETCH_DAY_MEALS", 1),
    FETCH_PRODUCT_DETAILS("FETCH_PRODUCT_DETAILS", 2),
    FETCH_PRODUCT_IMAGE("FETCH_PRODUCT_IMAGE", 3),
    CREATE_AND_ADD_MEAL("CREATE_AND_ADD_MEAL", 4),
    FETCH_ALL_MEALS("FETCH_ALL_MEALS", 5),
    CREATE_MEAL_PLAN("CREATE_MEAL_PLAN", 6),
    FETCH_MEAL_PLAN("FETCH_MEAL_PLAN", 7),
    FETCH_FOOD_LIST("FETCH_FOOD_LIST", 8),
    CREATE_FOOD("CREATE_FOOD", 9),
    FETCH_PLAN_LIST("FETCH_MEAL_PLAN_LIST", 10), CHECK_SYSTEM("CHECK_SYSTEM",11 );


    String name;
    int code;

    private UseCases(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}