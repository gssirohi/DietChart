package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface CreateFoodUseCase extends UseCase {
    void execute(final Callback callback, Food food, boolean showLoader);

    interface Callback {
        void onError(AppErrors error);

        void onFoodCreated(Food food);
    }
}
