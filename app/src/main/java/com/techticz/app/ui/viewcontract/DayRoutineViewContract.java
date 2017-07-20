package com.techticz.app.ui.viewcontract;

import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.ui.viewmodel.contract.IMealRoutineViewModel;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;

import java.util.List;

/**
 * Created by gssirohi on 5/7/16.
 */
public interface DayRoutineViewContract extends ViewContract {
    void onDayRoutinesFetched(int day, List<IMealRoutineViewModel> routines);

    void onMealAdded(int routineId, int day, Meal meal);

    void onMealsFetched(List<IMealViewModel> viewModels, String searchKey);

    void onPopularMealsFetched(List<IMealViewModel> viewModels);
}
