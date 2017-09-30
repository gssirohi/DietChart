package com.techticz.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dietchart.auth.utils.LoginUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.Key;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.FetchBlobUseCase;
import com.techticz.app.domain.interactor.IExtractNutritientUseCase;
import com.techticz.app.domain.interactor.MealPlanUseCase;
import com.techticz.app.domain.interactor.FetchDayMealListInteractor;
import com.techticz.app.domain.interactor.FetchDayMealListUseCase;
import com.techticz.app.domain.interactor.GetMealPlanUseCase;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.domain.model.pojo.NutitionInfo;
import com.techticz.app.ui.customview.ProductCardView;
import com.techticz.app.ui.fragment.DailyRoutineTabFragment;
import com.techticz.app.ui.fragment.DashBoardFragment;
import com.techticz.app.ui.fragment.DayMealInfoFragment;
import com.techticz.app.ui.fragment.ProductDetailsFragment;

import com.techticz.app.ui.viewmodel.contract.IProductViewModel;
import com.techticz.app.utility.AppUtils;
import com.tecticz.powerkit.ui.customview.RoundImageView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class DailyRoutineActivity extends BaseActivity implements ProductCardView.ProductCardViewContract,
        GetMealPlanUseCase.Callback, FetchDayMealListUseCase.Callback,
        MealPlanUseCase.Callback, NavigationView.OnNavigationItemSelectedListener, FetchBlobUseCase.Callback {

    private OnResult resultWatcher;
    private DailyRoutineTabFragment chartFrag;
    private Long mealPlanId;
    private MealPlan mealPlan;
    private DashBoardFragment dashFrag;
    private ImageView iv_meal_plan;

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
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((AppBarLayout)findViewById(R.id.app_bar)).setExpanded(false,true);
        iv_meal_plan = (ImageView)findViewById(R.id.iv_meal_plan);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayPlanInfoFragment();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setUpNavigationDrawer(navigationView);
        mealPlanId = getIntent().getLongExtra("meal_plan_id", 0);

        dashFrag = DashBoardFragment.newInstance();
        chartFrag = DailyRoutineTabFragment.newInstance();
        fetchMealPlan();
    }

    private void displayPlanInfoFragment() {
        DayMealInfoFragment.newInstance().show(getSupportFragmentManager(), "dialog");
    }


    private void setUpNavigationDrawer(NavigationView navigationView) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return;
        ((RoundImageView)navigationView.getHeaderView(0).findViewById(R.id.riv_user_profile)).setUrl(user.getPhotoUrl().toString());
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.tv_user_name)).setText(user.getDisplayName());
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.tv_user_email)).setText(user.getEmail());
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.tv_user_phone)).setText(user.getPhoneNumber());
        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNavigator().navigateToLoginActivity(DailyRoutineActivity.this);
            }
        });
    }

    public void fetchMealPlan() {
        GetMealPlanUseCase usecase = (GetMealPlanUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_MEAL_PLAN);
        usecase.execute(this, mealPlanId, true);
    }

    public static Intent getCallingIntent(Context context, Long planId) {
        Intent i = new Intent(context, DailyRoutineActivity.class);
        i.putExtra("meal_plan_id", planId);
        return i;
    }

    public void fetchRoutinesForDay(int day, OnResult resultWatcher) {
        this.resultWatcher = resultWatcher;
        // getPresenter().onFetchRoutinesByDay(true,day);

        FetchDayMealListInteractor usecase = (FetchDayMealListInteractor) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_DAY_MEALS);
        usecase.execute(this, day, getDayMealIdsByDay(day), true);
    }

    public DayMeals getDayMealIdsByDay(int day) {
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
            Parcelable mealParcel = data.getParcelableExtra("meal");
            int routineId = data.getIntExtra("routineId", 0);
            DayMeals meals = getDayMealIdsByDay(day);

            Meal meal = null;
            if(mealParcel != null)
            {
               meal = Parcels.unwrap(mealParcel);
            }
            meals.setMealOnRoutine(routineId, mealId );

            if(meal == null) {
                syncMealPlan();
            } else {
                getMealPlan().extractNutritions().add(meal.extractNutritions());
                resultWatcher.updateMealInRoutine(day, routineId, meal);
                syncMealPlan();
            }
        }
    }

    private void syncMealPlan() {
        MealPlanUseCase usecase = (MealPlanUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.CREATE_MEAL_PLAN);
        usecase.updateMealPlan(this, getMealPlan(), true);
    }

    public void removeMealFromRoutine(int day, int routineId, Meal meal){
        DayMeals dayMeals = getDayMealIdsByDay(day);
        dayMeals.setMealOnRoutine(routineId,0l);
        syncMealPlan();
    }

    public int getCurrentActiveSection() {
        return chartFrag.getActiveSection();
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onBlobFetched(String blobKey, Bitmap bitmap) {
        if(bitmap != null){
            //iv_meal_plan.setImageBitmap(AppUtils.blurRenderScript(this,bitmap,10));
            iv_meal_plan.setImageBitmap(bitmap);
//            Glide.with(this).load(bitmap)
//                    .apply(bitmapTransform(new BlurTransformation(25)))
//                    .into(iv_meal_plan);
        }
    }

    @Override
    public void onMealPlanCreated(MealPlan planWithId) {
        this.mealPlan = planWithId;
        this.mealPlanId = planWithId.getUid();
        loadMealPlanImage();
        chartFrag.loadRoutinesOfTheDay(getCurrentActiveSection());
    }

    @Override
    public void onRoutineMealList(int day, List<MealRoutine> routines) {
        resultWatcher.updateRoutineList(day, routines);
    }

    @Override
    public void onMealPlanFetched(MealPlan planWithId) {
        if(planWithId != null) {
            this.mealPlan = planWithId;
            ((CollapsingToolbarLayout) findViewById(R.id.toolbar_layout)).setTitle(planWithId.getName());

            loadMealPlanImage();
            showDashBoard();
        } else {
            //display error view or open meal plan browse screen
        }
    }

    private void loadMealPlanImage() {
        FetchBlobUseCase usecase = (FetchBlobUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_BLOB);
        usecase.execute(this,false,getMealPlan().getBlobKey(),getMealPlan().getBlobServingUrl());
    }
    @Override
    public void onBackPressed() {
/*
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
            return;
        }
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_plan) {
            String emailOrPhone = getMealPlan().getCreator();
            if(LoginUtils.matchUser(emailOrPhone)){
                getNavigator().navigateToUpdateMealPlanActivity(getMealPlan());
            } else {
                showCreateCopyDialog();
            }
            return true;
        }
         else if (id == R.id.action_auto_load) {
            String emailOrPhone = getMealPlan().getCreator();
            if(LoginUtils.matchUser(emailOrPhone)){
                showAutoLoadDialog();
            } else {
                showCreateCopyDialog();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAutoLoadDialog() {
        (new MaterialDialog.Builder(this))
                .title("Load Meals")
                .content("Meals will be loaded in all the routine automatically considering" +
                        " daily calory needs \n \n Note: already added meals will be untouched")
                .positiveText("Load Meals")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        autoLoadMeals();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void autoLoadMeals() {
        MealPlanUseCase usecase = (MealPlanUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.CREATE_MEAL_PLAN);
        List<Integer> pRoutines = new ArrayList<>();
        pRoutines.add(1);pRoutines.add(2);pRoutines.add(3);pRoutines.add(4);pRoutines.add(5);pRoutines.add(6);pRoutines.add(7);
        usecase.autoLoadMealPlan(this, mealPlan, true, pRoutines, true);
    }

    private void showCreateCopyDialog() {
        (new MaterialDialog.Builder(this))
                .title("Restricted Access")
                .content("Your don't have access to modify this plan." +
                        "However you can create a new copy and modify it accordingly")
                .positiveText("Create Copy")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        getNavigator().navigateToCopyMealPlanActivity(getMealPlan());
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dash_board) {
            showDashBoard();
        } else if (id == R.id.nav_diet_chart) {
            showDietChart();
        } else if (id == R.id.nav_meal_plans) {
            getNavigator().navigateToBrowseMealPlanActivity();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDashBoard() {
        findViewById(R.id.fab).setVisibility(View.GONE);
        FragmentTransaction ft = null;
        ft = getSupportFragmentManager().beginTransaction();
        if(getSupportFragmentManager().findFragmentByTag("dash") == null){
            if(dashFrag == null){
                dashFrag = DashBoardFragment.newInstance();
            }
            ft.add(R.id.fragment_container,dashFrag,"dash");
            ft.show(dashFrag);
        } else {
            ft.show(getSupportFragmentManager().findFragmentByTag("dash"));
        }
        if(getSupportFragmentManager().findFragmentByTag("chart") != null){
            ft.hide(getSupportFragmentManager().findFragmentByTag("chart"));
        }
        ft.commit();
    }

    private void showDietChart() {
        findViewById(R.id.fab).setVisibility(View.VISIBLE);
        FragmentTransaction ft = null;
        ft = getSupportFragmentManager().beginTransaction();
        if(getSupportFragmentManager().findFragmentByTag("chart") == null){
            if(chartFrag == null){
                chartFrag = DailyRoutineTabFragment.newInstance();
            }
            ft.add(R.id.fragment_container,chartFrag,"chart");
            ft.show(chartFrag);
        } else {
            ft.show(getSupportFragmentManager().findFragmentByTag("chart"));
        }

        if(getSupportFragmentManager().findFragmentByTag("dash") != null){
            ft.hide(getSupportFragmentManager().findFragmentByTag("dash"));
        }
        ft.commit();
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public interface OnResult {
        void updateRoutineList(int day, List<MealRoutine> routines);
        void updateMealInRoutine(int day,int routineId, Meal meal);
    }
}
