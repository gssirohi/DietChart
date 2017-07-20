package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface CreateMealPlanUseCase extends UseCase {
    void execute(final Callback callback, MealPlan plan, boolean showLoader);

    interface Callback {
        void onError(AppErrors error);

        void onMealPlanCreated(MealPlan planWithId);
    }
}
