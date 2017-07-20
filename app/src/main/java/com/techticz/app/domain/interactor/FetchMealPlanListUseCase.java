package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.MealPlan;

import java.util.List;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface FetchMealPlanListUseCase extends UseCase {
    void execute(final Callback callback, boolean showLoader, String searchKey, boolean isMyPlan);

    interface Callback {
        void onError(AppErrors error);

        void onMealPlanListFetched(List<MealPlan> foods, String searchKey, boolean isMyPlan);
    }
}
