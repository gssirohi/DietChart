package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealRoutine;

import java.util.List;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface FetchAllMealsUseCase extends UseCase {
    void execute(final Callback callback, boolean showLoader, String searchKey, int[] mealIds);

    interface Callback {
        void onError(AppErrors error);

        void onAllMealsFetched(List<Meal> meals, String searchKey);
    }
}
