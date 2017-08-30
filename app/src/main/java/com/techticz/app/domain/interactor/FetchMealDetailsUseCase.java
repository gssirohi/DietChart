package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.Meal;

import java.util.List;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface FetchMealDetailsUseCase extends UseCase {
    void execute(final Callback callback, boolean showLoader, long mealId);

    interface Callback {
        void onError(AppErrors error);

        void onMealDetailsFetched(Meal meal);
    }
}
