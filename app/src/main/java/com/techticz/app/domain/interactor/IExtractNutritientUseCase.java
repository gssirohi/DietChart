package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.NutitionInfo;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;


/**
 * Created by gssirohi on 29/8/16.
 */
public interface IExtractNutritientUseCase extends UseCase {
    void execute(final Callback callback, boolean showLoader,Meal meal,DayMeals daymeals,MealPlan plan);

    interface Callback {
        void onError(AppErrors error);
        void onNutritionFetched(NutitionInfo meal,NutitionInfo dayMeals,NutitionInfo plan);
    }
}
