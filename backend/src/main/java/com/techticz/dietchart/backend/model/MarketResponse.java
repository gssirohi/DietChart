package com.techticz.dietchart.backend.model;

import com.google.api.server.spi.response.CollectionResponse;
import com.techticz.dietchart.backend.entities.FoodEntity;
import com.techticz.dietchart.backend.entities.MealEntity;
import com.techticz.dietchart.backend.entities.MealPlanEntityItem;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 17/9/17.
 */

public class MarketResponse {

    private CollectionResponse<MealEntity> exclusiveMeals;
    private CollectionResponse<MealPlanEntityItem> plans;
    private CollectionResponse<FoodEntity> exclusiveFood;

    public void setExclusiveMeals(CollectionResponse<MealEntity> exclusiveMeals) {
        this.exclusiveMeals = exclusiveMeals;
    }

    public CollectionResponse<MealEntity> getExclusiveMeals() {
        return exclusiveMeals;
    }

    public void setPlans(CollectionResponse<MealPlanEntityItem> plans) {
        this.plans = plans;
    }

    public CollectionResponse<MealPlanEntityItem> getPlans() {
        return plans;
    }

    public void setExclusiveFood(CollectionResponse<FoodEntity> exclusiveFood) {
        this.exclusiveFood = exclusiveFood;
    }

    public CollectionResponse<FoodEntity> getExclusiveFood() {
        return exclusiveFood;
    }
}
