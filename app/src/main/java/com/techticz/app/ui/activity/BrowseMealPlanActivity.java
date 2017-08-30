package com.techticz.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.Key;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.FetchMealPlanListUseCase;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.ui.adapter.BrowseFoodRecyclerViewAdapter;
import com.techticz.app.ui.adapter.BrowsePlanRecyclerViewAdapter;
import com.techticz.app.ui.adapter.MealPlanPagerAdapter;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.DiscreteScrollItemTransformer;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class BrowseMealPlanActivity extends BaseActivity implements FetchMealPlanListUseCase.Callback,
        MealPlanPagerAdapter.CallBack,BrowsePlanRecyclerViewAdapter.MealPlanViewContract {

    private EditText searchBox;
    private ImageView ivClear;
    private ProgressBar pb;
    private ViewGroup searchCard;
    private ViewGroup popularCard;
    private RecyclerView searchList;
    private RecyclerView myPlanList;
    private String currentSearchKey;

    List<MealPlan> searchData;
    List<MealPlan> myPlansData;
    private BrowsePlanRecyclerViewAdapter searchAdapter;
    private MealPlanPagerAdapter myPlansAdapter;
    private FetchMealPlanListUseCase myPlanListUseCase;
    private FetchMealPlanListUseCase searchFoodUseCase;
    private DiscreteScrollView scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_meal_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCreatePlanClick();
            }
        });
        initViewFields();
        initData();
        initUI();
        setUpListners();
    }

    private void initData() {

        searchData = new ArrayList<>();
        myPlansData = new ArrayList<>();

        searchAdapter = new BrowsePlanRecyclerViewAdapter(searchData, this);
        myPlansAdapter = new MealPlanPagerAdapter(myPlansData,this);
        scroller.setAdapter(myPlansAdapter);
        //   getPresenter().fetchPopularMeals();
    }

    private void initUI() {
/*
        pb.setVisibility(GONE);
        searchList.setLayoutManager(new LinearLayoutManager(this));
        myPlanList.setLayoutManager(new LinearLayoutManager(this));
        searchList.setAdapter(searchAdapter);
        myPlanList.setAdapter(myPlansAdapter);
*/

        scroller.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build());
        if (myPlanListUseCase == null) {
            myPlanListUseCase = (FetchMealPlanListUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_PLAN_LIST);
        }
        myPlanListUseCase.execute(this, false, "", true);
    }

    private void setUpListners() {
        /*searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 2)
                    performSearchForKey(editable.toString());
            }
        });*/
    }

    private void performSearchForKey(String s) {
        currentSearchKey = s.toString();
        pb.setVisibility(View.VISIBLE);

        if (searchFoodUseCase == null) {
            searchFoodUseCase = (FetchMealPlanListUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_PLAN_LIST);
        }
        searchFoodUseCase.execute(this, true, currentSearchKey, false);
        //getPresenter().onFetchAllMeals(currentSearchKey);
    }

    private void initViewFields() {
        /*searchBox = (EditText) findViewById(R.id.et_search_box);
        ivClear = (ImageView) findViewById(R.id.iv_clear_text);
        pb = (ProgressBar) findViewById(R.id.pb);
        searchCard = (ViewGroup) findViewById(R.id.card_search_result);
        popularCard = (ViewGroup) findViewById(R.id.card_popular_meals);
        searchList = (RecyclerView) findViewById(R.id.recycler_view_search);
        myPlanList = (RecyclerView) findViewById(R.id.recycler_view_popular);*/

        scroller = (DiscreteScrollView)findViewById(R.id.scroller);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.browse_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_create_meal_plan:
                handleCreatePlanClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleCreatePlanClick() {
        getNavigator().navigateToCreateMealPlanActivity();
    }

    public static Intent getCallingIntent(Context context) {
        Intent i = new Intent(context, BrowseMealPlanActivity.class);
        return i;
    }


    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onMealPlanListFetched(List<MealPlan> plans, String searchKey, boolean isMyPlan) {
        if(plans == null) plans = new ArrayList<>();
//        pb.setVisibility(GONE);
        if (isMyPlan) {
            myPlansData.clear();
            myPlansData.addAll(plans);
            myPlansAdapter.notifyDataSetChanged();
        } else if (currentSearchKey.equalsIgnoreCase(searchKey)) {
            pb.setVisibility(GONE);
            searchData.clear();
            searchData.addAll(plans);
            searchAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onMealPlanItemClicked(MealPlan mealPlan) {
        Long id = mealPlan.getUid();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("plan", id);
        editor.commit();

        getNavigator().navigateToFoodChartActivity(id);
        finish();
    }


}

