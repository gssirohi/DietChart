package com.techticz.app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.FoodCategory;
import com.techticz.app.constant.FoodType;
import com.techticz.app.constant.Key;
import com.techticz.app.constant.Routines;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.CreateMealUseCase;
import com.techticz.app.domain.interactor.FetchFoodListUseCase;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.ui.customview.FoodListItemView;

import java.util.ArrayList;
import java.util.List;

public class CreateMealActivity extends BaseActivity implements FetchFoodListUseCase.Callback, CreateMealUseCase.Callback {

    private TextInputLayout til_meal_name;
    private TextInputLayout til_meal_desc;
    private TextInputLayout til_meal_category;
    private TextInputLayout til_meal_type;
    private TextInputLayout til_meal_pref_routine;
    private Button b_add_food;
    private LinearLayout ll_food_container;
    private LinearLayout ll_nutri_container;
    private int mealPlanId;
    private int routineId;
    private int day;
    private Long mealId;
    private FetchFoodListUseCase fetchFoodUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSubmitClick();
            }
        });
        initData();
        initUI();
    }

    private void initData() {
        mealPlanId = getIntent().getIntExtra("mealPlanId", 0);
        routineId = getIntent().getIntExtra("routineId", 0);
        day = getIntent().getIntExtra("day", -1);
    }

    private void initUI() {
        til_meal_name = (TextInputLayout) findViewById(R.id.til_meal_name);
        til_meal_desc = (TextInputLayout) findViewById(R.id.til_meal_desc);
        til_meal_category = (TextInputLayout) findViewById(R.id.til_meal_category);
        til_meal_type = (TextInputLayout) findViewById(R.id.til_meal_type);
        til_meal_pref_routine = (TextInputLayout) findViewById(R.id.til_meal_pref_routines);
        b_add_food = (Button) findViewById(R.id.b_meal_add_food);
        ll_food_container = (LinearLayout) findViewById(R.id.ll_food_item);
        ll_nutri_container = (LinearLayout) findViewById(R.id.ll_nutritient_view_container);

        b_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddFoodClick();
            }
        });

        til_meal_type.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTypeClick();
            }
        });

        til_meal_category.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCategoryClick();
            }
        });
        til_meal_pref_routine.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePrefRoutineClick();
            }
        });
    }


    private void handlePrefRoutineClick() {
        new MaterialDialog.Builder(this)
                .title("Choose Preferred Routines")
                .items(Routines.getAllNames())
                .itemsCallbackMultiChoice(new Integer[]{-1}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        setMealItemValues(til_meal_pref_routine, text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void handleTypeClick() {
        new MaterialDialog.Builder(this)
                .title("Select Meal Type")
                .items(FoodType.getAllNames())
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        setMealItemValue(til_meal_type, text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void handleCategoryClick() {
        new MaterialDialog.Builder(this)
                .title("Select Meal Category")
                .items(FoodCategory.getAllNames())
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        setMealItemValue(til_meal_category, text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void setMealItemValues(TextInputLayout vg, CharSequence[] values) {
        vg.setTag(values);
        String result = "";
        int size = values.length;
        for (int i = 0; i < size; i++) {
            result = result + values[i];
            if (i != size - 1) {
                result = result + ",";
            }
        }
        vg.getEditText().setText(result);
    }

    private void setMealItemValue(TextInputLayout vg, CharSequence text) {
        vg.getEditText().setText(text);
    }

    private void handleAddFoodClick() {
        getNavigator().navigateToBrowseFoodActivity(this, mealId);
    }

    private void handleSubmitClick() {
        //create Meal in DB
        if (!isvalidated()) {
            return;
        }

        Meal meal = new Meal();
        meal.setName(til_meal_name.getEditText().getText().toString());
        meal.setDesc(til_meal_desc.getEditText().getText().toString());
        meal.setType(FoodType.getIdByName(til_meal_type.getEditText().getText().toString()));
        meal.setCategory(FoodCategory.getIdByName(til_meal_category.getEditText().getText().toString()));
        String prefRoutines = til_meal_pref_routine.getEditText().getText().toString();
        String[] routines = prefRoutines.split(",");
        List<Integer> pRoutines = new ArrayList<>();
        for (String routine : routines) {
            int id = Routines.getIdByName(routine);
            pRoutines.add(id);
        }
        meal.setPrefRoutine(pRoutines);

        int children = ll_food_container.getChildCount();
        List<Long> foods = new ArrayList<>();
        for (int i = 0; i < children; i++) {
            FoodListItemView view = (FoodListItemView) ll_food_container.getChildAt(i);
            Food food = view.getViewModel();
            long uid = food.getUid();
            foods.add(uid);
        }
        meal.setFoodIds(foods);

        CreateMealUseCase usecase = (CreateMealUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.CREATE_AND_ADD_MEAL);
        usecase.execute(this, meal, true);
    }

    private boolean isvalidated() {
        if (TextUtils.isEmpty(til_meal_name.getEditText().getText())) {
            Toast.makeText(this, "Please enter Meal Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(til_meal_desc.getEditText().getText())) {
            Toast.makeText(this, "Please enter Meal Description", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(til_meal_type.getEditText().getText())) {
            Toast.makeText(this, "Please Select Meal Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(til_meal_category.getEditText().getText())) {
            Toast.makeText(this, "Please Select Meal Category", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(til_meal_category.getEditText().getText())) {
            Toast.makeText(this, "Please Choose Preferred Routines", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ll_food_container.getChildCount() == 0) {
            Toast.makeText(this, "Please Add Food in the meal", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Key.BROWSE_FOOD && resultCode == Activity.RESULT_OK) {
            Long foodId = data.getLongExtra("foodId", 0);
            if (foodId != 0)
                fetchFoodWithId(foodId);
        }
    }

    private void fetchFoodWithId(Long foodId) {
        if (fetchFoodUseCase == null) {
            fetchFoodUseCase = (FetchFoodListUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_FOOD_LIST);
        }
        Long[] ids = new Long[1];
        ids[0] = foodId;
        fetchFoodUseCase.execute(this, false, "", ids);
    }

    public static Intent getCallingIntent(Activity context, int mealPlanId) {
        Intent i = new Intent(context, CreateMealActivity.class);
        i.putExtra("mealPlanId", mealPlanId);
        return i;
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onMealCreated(Meal meal) {
        if (meal != null) {
            mealId = meal.getUid();
            Intent i = new Intent();
            i.putExtra("mealId", meal.getUid());
            i.putExtra("mealPlanId", mealPlanId);
            i.putExtra("routineId", routineId);
            i.putExtra("day", day);
            setResult(RESULT_OK, i);
            finish();
        }
    }

    @Override
    public void onFoodListFetched(List<Food> foods, String searchKey) {
        //basically just one food fetched
        Food food = foods.get(0);
        FoodListItemView view = new FoodListItemView(this);
        view.fillDetails(food);
        ll_food_container.addView(view);
    }
}
