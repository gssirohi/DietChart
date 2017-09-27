package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.dietchart.backend.appUserApi.model.AppUser;
import com.techticz.dietchart.backend.appUserApi.model.UserLoginResponse;

import java.util.List;


/**
 * Created by gssirohi on 29/8/16.
 */
public interface LoginUseCase extends UseCase {
    void execute(final Callback callback, AppUser appUser, boolean showLoader);

    interface Callback {
        void onError(AppErrors error);

        void onLoggedIn(UserLoginResponse loginResponse, List<MealPlan> mealPlans);
    }
}
