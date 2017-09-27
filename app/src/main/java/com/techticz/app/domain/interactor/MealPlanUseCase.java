package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;

import java.util.List;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface MealPlanUseCase extends UseCase {
    void createMealPlan(final Callback callback, MealPlan plan, boolean autoLoad, List<Integer> pRoutines, boolean showLoader);
    void updateMealPlan(final Callback callback, MealPlan plan, boolean showLoader);
    void autoLoadMealPlan(final Callback callback, MealPlan plan, boolean autoLoad, List<Integer> pRoutines, boolean showLoader);

    interface Callback {
        void onError(AppErrors error);

        void onMealPlanCreated(MealPlan planWithId);
    }
}
