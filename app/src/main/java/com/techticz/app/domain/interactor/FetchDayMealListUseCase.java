package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.Products;
import com.techticz.app.domain.model.ProductModel;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealRoutine;

import java.util.List;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface FetchDayMealListUseCase extends UseCase {
    void execute(final Callback callback, int day, DayMeals dayMeals, boolean showLoader);

    interface Callback {
        void onError(AppErrors error);

        void onRoutineMealList(int day, List<MealRoutine> routines);
    }
}
