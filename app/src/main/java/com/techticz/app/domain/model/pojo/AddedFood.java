package com.techticz.app.domain.model.pojo;

import org.parceler.Parcel;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 10/8/17.
 */

@Parcel
public class AddedFood {
     Long foodId;
     int serving;
     Food food;

    public AddedFood() {
    }

    public AddedFood(Food food){
        this.foodId = food.getUid();
        this.food = food;
        this.serving = 1;
    }
    public AddedFood(Long foodId, int serving) {
        this.foodId = foodId;
        this.serving = serving;
    }

    public AddedFood(Food food, int serving) {
        this.foodId = food.getUid();
        this.food = food;
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

    public void setFood(Food food) {
        this.food = food;
    }

    public Food getFood() {
        return food;
    }
}
