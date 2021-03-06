package com.techticz.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
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
import com.techticz.app.domain.interactor.FetchAllMealsUseCase;
import com.techticz.app.domain.interactor.FetchMealListUseCase;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.ui.adapter.BrowseMealRecyclerViewAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class BrowseMealActivity extends BaseActivity implements BrowseMealRecyclerViewAdapter.MealViewContract, FetchMealListUseCase.Callback {

    private EditText searchBox;
    private ImageView ivClear;
    private ProgressBar pb;
    private ViewGroup searchCard;
    private ViewGroup popularCard;
    private RecyclerView searchList;
    private RecyclerView popularList;
    private String currentSearchKey;

    List<Meal> searchData;
    List<Meal> popularData;
    private BrowseMealRecyclerViewAdapter searchAdapter;
    private BrowseMealRecyclerViewAdapter popularAdapter;
    private int routineId;
    private int day;
    private int mealPlanId;
    private FetchMealListUseCase searchMealUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_meal);
        initViewFields();
        initData();
        initUI();
        setUpListners();
    }

    private void initData() {

        routineId = getIntent().getIntExtra("routineId", 0);
        day = getIntent().getIntExtra("day", -1);
        mealPlanId = getIntent().getIntExtra("mealPlanId", 0);

        searchData = new ArrayList<>();
        popularData = new ArrayList<>();

        searchAdapter = new BrowseMealRecyclerViewAdapter(searchData, this);
        popularAdapter = new BrowseMealRecyclerViewAdapter(popularData, this);

        if (searchMealUseCase == null) {
            searchMealUseCase = (FetchMealListUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_MEAL_LIST);
        }
        //getPresenter().fetchPopularMeals();
    }

    private void initUI() {
        pb.setVisibility(GONE);
        searchList.setLayoutManager(new LinearLayoutManager(this));
        popularList.setLayoutManager(new LinearLayoutManager(this));
        searchList.setAdapter(searchAdapter);
        popularList.setAdapter(popularAdapter);
        searchMealUseCase.execute(this,false,"",new long[]{});
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
        //getPresenter().onFetchAllMeals(currentSearchKey);
        searchMealUseCase.execute(this, false, currentSearchKey, new long[]{});
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
    public void onMealItemClicked(Meal viewModel) {
        Intent intent = new Intent();
        intent.putExtra("routineId", routineId);
        intent.putExtra("day", day);
        intent.putExtra("mealId", viewModel.getUid());
        viewModel.setBitmap(null);
        intent.putExtra("meal", Parcels.wrap(viewModel));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.browse_meal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_create_meal:
                handleCreateMealClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleCreateMealClick() {
        getNavigator().navigateToCreateMealActivity( this, mealPlanId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Key.CREATE_MEAL && resultCode == Activity.RESULT_OK) {
            Long mealId = data.getLongExtra("mealId", 0);
            Parcelable mealParcel = data.getParcelableExtra("meal");
//            int mealPlanId = data.getIntExtra("mealPlanId",0);
//            int routineId = data.getIntExtra("routineId",0);
//            int day = data.getIntExtra("day",-1);
            if (mealId != null && mealId != 0 && mealParcel != null) {

                Intent intent = new Intent();
                intent.putExtra("routineId", routineId);
                intent.putExtra("day", day);
                intent.putExtra("mealId", mealId);
                intent.putExtra("meal",mealParcel);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    public static Intent getCallingIntent(Context context, Long mealPlanId, int day, int routineId) {
        Intent i = new Intent(context, BrowseMealActivity.class);
        i.putExtra("routineId", routineId);
        i.putExtra("day", day);
        i.putExtra("mealPlanId", mealPlanId);
        return i;
    }


    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onMealListFetched(List<Meal> meals, String searchKey) {
        if (!TextUtils.isEmpty(searchKey) && currentSearchKey.equalsIgnoreCase(searchKey)) {
            pb.setVisibility(GONE);
            searchData.clear();
            searchData.addAll(meals);
            searchAdapter.notifyDataSetChanged();
        } else {
            pb.setVisibility(GONE);
            popularData.clear();
            popularData.addAll(meals);
            popularAdapter.notifyDataSetChanged();
        }
    }
}
