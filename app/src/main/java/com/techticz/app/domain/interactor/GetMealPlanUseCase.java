package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.MealPlan;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface GetMealPlanUseCase extends UseCase {
    void execute(final Callback callback, Long id, boolean showLoader);

    interface Callback {
        void onError(AppErrors error);

        void onMealPlanFetched(MealPlan planWithId);
    }
}
