package com.techticz.app.domain.model.pojo;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 10/8/17.
 */

public class AddedFood {
    private Long foodId;
    private int serving;

    public AddedFood(Long foodId, int serving) {
        this.foodId = foodId;
        this.serving = serving;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }
}
