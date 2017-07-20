package com.techticz.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.Key;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.CreateMealPlanUseCase;
import com.techticz.app.domain.interactor.FetchDayMealListInteractor;
import com.techticz.app.domain.interactor.FetchDayMealListUseCase;
import com.techticz.app.domain.interactor.GetMealPlanUseCase;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.ui.customview.ProductCardView;
import com.techticz.app.ui.fragment.DailyRoutineTabFragment;
import com.techticz.app.ui.fragment.ProductDetailsFragment;

import com.techticz.app.ui.viewmodel.contract.IMealRoutineViewModel;
import com.techticz.app.ui.viewmodel.contract.IProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class DailyRoutineActivity extends BaseActivity implements ProductCardView.ProductCardViewContract, GetMealPlanUseCase.Callback, FetchDayMealListUseCase.Callback, CreateMealPlanUseCase.Callback {

    private List<Meal> meals = new ArrayList<>();
    private OnResult resultWatcher;
    private DailyRoutineTabFragment tabfrag;
    private Long mealPlanId;
    private MealPlan mealPlan;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_chart);
        mealPlanId = getIntent().getLongExtra("meal_plan_id", 0);
        fetchMealPlan();
    }

    public void fetchMealPlan() {
        GetMealPlanUseCase usecase = (GetMealPlanUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_MEAL_PLAN);
        usecase.execute(this, mealPlanId, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_trips_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static Intent getCallingIntent(Context context, Long planId) {
        Intent i = new Intent(context, DailyRoutineActivity.class);
        i.putExtra("meal_plan_id", planId);
        return i;
    }


    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
            return;
        }

        super.onBackPressed();
    }

    public void fetchRoutinesForDay(int day, OnResult resultWatcher) {
        this.resultWatcher = resultWatcher;
        // getPresenter().onFetchRoutinesByDay(true,day);

        FetchDayMealListInteractor usecase = (FetchDayMealListInteractor) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_DAY_MEALS);
        usecase.execute(this, day, getDayMealIdsByDay(day), true);
    }

    private DayMeals getDayMealIdsByDay(int day) {
        if (mealPlan == null) return null;
        switch (day) {
            case 0:
                return mealPlan.getMondayMeals();
            case 1:
                return mealPlan.getTuesdayMeals();
            case 2:
                return mealPlan.getWednesdayMeals();
            case 3:
                return mealPlan.getThursdayMeals();
            case 4:
                return mealPlan.getFridayMeals();
            case 5:
                return mealPlan.getSaturdayMeals();
            case 6:
                return mealPlan.getSundayMeals();

        }
        return null;
    }

    @Override
    public void onCardViewItemClicked(IProductViewModel viewModel) {
        ProductDetailsFragment newFragment = ProductDetailsFragment.newInstance(viewModel.getResourceUri());
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //TODO: put tablet and mobile logic here
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Key.ADD_MEAL && resultCode == Activity.RESULT_OK) {
            int day = data.getIntExtra("day", -1);
            long mealId = data.getLongExtra("mealId", 0);
            int routineId = data.getIntExtra("routineId", 0);

            DayMeals meals = getDayMealIdsByDay(day);
            meals.setMealOnRoutine(routineId, mealId);

            CreateMealPlanUseCase usecase = (CreateMealPlanUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.CREATE_MEAL_PLAN);
            usecase.execute(this, mealPlan, true);

        }
    }


    public int getCurrentActiveSection() {
        return tabfrag.getActiveSection();
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onMealPlanCreated(MealPlan planWithId) {
        tabfrag.loadRoutinesOfTheDay(getCurrentActiveSection());
    }

    @Override
    public void onRoutineMealList(int day, List<MealRoutine> routines) {
        resultWatcher.updateRoutineList(day, routines);
    }

    @Override
    public void onMealPlanFetched(MealPlan planWithId) {
        this.mealPlan = planWithId;
        tabfrag = DailyRoutineTabFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, tabfrag, "tabs").commit();
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }


    public interface OnResult {
        void updateRoutineList(int day, List<MealRoutine> routines);

    }
}
