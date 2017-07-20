package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;

import java.util.List;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface FetchFoodListUseCase extends UseCase {
    void execute(final Callback callback, boolean showLoader, String searchKey, Long[] foodIds);

    interface Callback {
        void onError(AppErrors error);

        void onFoodListFetched(List<Food> foods, String searchKey);
    }
}
