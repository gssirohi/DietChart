package com.techticz.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.techticz.app.domain.interactor.FetchFoodListUseCase;
import com.techticz.app.domain.interactor.UseCase;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.ui.adapter.BrowseFoodRecyclerViewAdapter;
import com.techticz.app.ui.adapter.BrowseMealRecyclerViewAdapter;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class BrowseFoodActivity extends BaseActivity implements BrowseFoodRecyclerViewAdapter.FoodViewContract, FetchFoodListUseCase.Callback {

    private EditText searchBox;
    private ImageView ivClear;
    private ProgressBar pb;
    private ViewGroup searchCard;
    private ViewGroup popularCard;
    private RecyclerView searchList;
    private RecyclerView popularList;
    private String currentSearchKey;

    List<Food> searchData;
    List<Food> popularData;
    private BrowseFoodRecyclerViewAdapter searchAdapter;
    private BrowseFoodRecyclerViewAdapter popularAdapter;

    private FetchFoodListUseCase searchFoodUseCase;
    private int mealId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_food);
        initViewFields();
        initData();
        initUI();
        setUpListners();
    }

    private void initData() {

        mealId = getIntent().getIntExtra("mealId", 0);
        searchData = new ArrayList<>();
        popularData = new ArrayList<>();

        searchAdapter = new BrowseFoodRecyclerViewAdapter(searchData, this);
        popularAdapter = new BrowseFoodRecyclerViewAdapter(popularData, this);
        //   getPresenter().fetchPopularMeals();
    }

    private void initUI() {
        pb.setVisibility(GONE);
        searchList.setLayoutManager(new LinearLayoutManager(this));
        popularList.setLayoutManager(new LinearLayoutManager(this));
        searchList.setAdapter(searchAdapter);
        popularList.setAdapter(popularAdapter);
    }

    private void setUpListners() {
        searchBox.addTextChangedListener(new TextWatcher() {
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
        });
    }

    private void performSearchForKey(String s) {
        currentSearchKey = s.toString();
        pb.setVisibility(View.VISIBLE);

        if (searchFoodUseCase == null) {
            searchFoodUseCase = (FetchFoodListUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_FOOD_LIST);
        }
        searchFoodUseCase.execute(this, false, currentSearchKey, new Long[]{});
        //getPresenter().onFetchAllMeals(currentSearchKey);
    }

    private void initViewFields() {
        searchBox = (EditText) findViewById(R.id.et_search_box);
        ivClear = (ImageView) findViewById(R.id.iv_clear_text);
        pb = (ProgressBar) findViewById(R.id.pb);
        searchCard = (ViewGroup) findViewById(R.id.card_search_result);
        popularCard = (ViewGroup) findViewById(R.id.card_popular_meals);
        searchList = (RecyclerView) findViewById(R.id.recycler_view_search);
        popularList = (RecyclerView) findViewById(R.id.recycler_view_popular);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.browse_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_create_food:
                handleCreateFoodClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleCreateFoodClick() {
        getNavigator().navigateToCreateFoodActivity(this, mealId);
    }

    public static Intent getCallingIntent(Context context, Long mealId) {
        Intent i = new Intent(context, BrowseFoodActivity.class);
        i.putExtra("mealId", mealId);
        return i;
    }


    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onFoodListFetched(List<Food> foods, String searchKey) {
        if (currentSearchKey.equalsIgnoreCase(searchKey)) {
            pb.setVisibility(GONE);
            searchData.clear();
            searchData.addAll(foods);
            searchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFoodItemClicked(Food food) {
        //RETURN BACK TO CREATE MEAL ACTIVITY
        Intent intent = new Intent();
        intent.putExtra("foodId", food.getUid());
        intent.putExtra("mealId", mealId);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Key.CREATE_FOOD && resultCode == Activity.RESULT_OK) {
            Long foodId = data.getLongExtra("foodId", 0);
            if (foodId != null && foodId != 0) {

                Intent intent = new Intent();
                intent.putExtra("foodId", foodId);
                intent.putExtra("mealId", mealId);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
