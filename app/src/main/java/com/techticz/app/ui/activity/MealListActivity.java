package com.techticz.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.techticz.app.R;
import com.techticz.app.ui.fragment.MealListFragment;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;

import java.util.List;

public class MealListActivity extends MealBaseActivity {

    private MealListFragment mealListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        mealListFragment = MealListFragment.newInstance(1);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mealListFragment, "tabs").commit();
        getPresenter().onFetchAllMeals("");
    }

    @Override
    public void onMealsFetched(List<IMealViewModel> viewModels, String searchKey) {
        super.onMealsFetched(viewModels, searchKey);
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MealListActivity.class);
    }
}
