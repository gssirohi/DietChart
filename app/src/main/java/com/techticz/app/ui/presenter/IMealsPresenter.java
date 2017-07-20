package com.techticz.app.ui.presenter;

import com.techticz.app.base.IBasePresenter;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.ui.viewcontract.DayRoutineViewContract;

/**
 * Created by gssirohi on 5/7/16.
 */
public interface IMealsPresenter extends IBasePresenter<DayRoutineViewContract> {

    void onFetchRoutinesByDay(boolean showLoader, int day);

    void onAddMeal(boolean isExisting, Meal meal, int routineId, int day);

    void onFetchAllMeals(String currentSearchKey);

    void fetchPopularMeals();
}
