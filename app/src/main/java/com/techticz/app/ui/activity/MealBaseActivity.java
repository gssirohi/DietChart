package com.techticz.app.ui.activity;

import android.os.Bundle;

import com.techticz.app.base.BaseActivity;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.ui.presenter.IMealsPresenter;
import com.techticz.app.ui.viewcontract.DayRoutineViewContract;
import com.techticz.app.ui.viewmodel.contract.IMealRoutineViewModel;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;

import java.util.List;

/**
 * Created by gssirohi on 14/6/17.
 */

public class MealBaseActivity extends BaseActivity implements DayRoutineViewContract {

    private IMealsPresenter iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public IMealsPresenter getPresenter() {
        return iPresenter;
    }

    @Override
    public void onError(String s) {

    }

    @Override
    public void onDayRoutinesFetched(int day, List<IMealRoutineViewModel> routines) {

    }

    @Override
    public void onMealAdded(int routineId, int day, Meal meal) {

    }

    @Override
    public void onMealsFetched(List<IMealViewModel> viewModels, String searchKey) {

    }

    @Override
    public void onPopularMealsFetched(List<IMealViewModel> viewModels) {

    }
}
