package com.techticz.app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dietchart.auth.utils.LoginUtils;
import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.FoodCategory;
import com.techticz.app.constant.FoodType;
import com.techticz.app.constant.LaunchMode;
import com.techticz.app.constant.Routines;
import com.techticz.app.constant.Servings;
import com.techticz.app.constant.Units;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.CreateFoodUseCase;
import com.techticz.app.domain.interactor.FetchBlobUseCase;
import com.techticz.app.domain.interactor.FetchFoodListUseCase;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.ui.adapter.NewFoodPagerAdapter;

import org.parceler.Parcels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.view.View.GONE;
import static com.techticz.app.constant.Key.GET_FROM_GALLERY;
import static com.techticz.app.constant.LaunchMode.CREATE;
import static com.techticz.app.constant.LaunchMode.EDIT;
import static com.techticz.app.constant.LaunchMode.VIEW;

public class CreateFoodActivity extends AppCompatActivity implements CreateFoodUseCase.Callback, FetchFoodListUseCase.Callback, FetchBlobUseCase.Callback {

    private TextInputLayout til_name;
    private TextInputLayout til_desc;
    private TextInputLayout til_type;
    private TextInputLayout til_category;
    private TextInputLayout til_pref_routines;
    private TextInputLayout til_calory;
    private TextInputLayout til_fat;
    private TextInputLayout til_protine;
    private TextInputLayout til_carbs;
    private TextInputLayout til_fiber;
    private TextInputLayout til_calcium;
    private TextInputLayout til_sodium;
    private TextInputLayout til_potassium;
    private TextInputLayout til_iron;
    private TextInputLayout til_vitaminA;
    private TextInputLayout til_vitaminB;
    private TextInputLayout til_vitaminC;
    private TextInputLayout til_cholestrol;
    private TextInputLayout til_sugar;
    private TextInputLayout til_zinc;
    private TextInputLayout til_magnisium;
    private TextInputLayout til_vitaminD;
    private TextInputLayout til_vitaminE;
    private TextInputLayout til_vitaminK;
    private TextInputLayout til_serving;
    private TextInputLayout til_unit;
    private TextInputLayout til_content;
    private FloatingActionButton fabB;
    private ImageView iv_food;
    private Bitmap foodBitmap;
    private long mealId;
    private long foodId;
    private LaunchMode mode;
    private CollapsingToolbarLayout collapsingToolBar;
    private Food food;
    private boolean editablility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolBar = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        collapsingToolBar.setTitle("Loading Food.");
        initData();
        initUI();
        provideData();
        changeMode(mode);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
    private void initData() {
        mealId = getIntent().getLongExtra("mealId",0);
        foodId = getIntent().getLongExtra("foodId",0);
        mode = LaunchMode.getById(getIntent().getIntExtra("mode",0));
    }

    private void provideData() {
        if(foodId != 0){
            collapsingToolBar.setTitle("Loading Food..");
            // FetchMealListUseCase usecase = (FetchMealListUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_MEAL_LIST);
            FetchFoodListUseCase usecase = (FetchFoodListUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_FOOD_LIST);
            usecase.execute(this,true,"",new Long[]{foodId});
        } else {
            collapsingToolBar.setTitle("Create Food");
        }
    }

    private void handleImageClick(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                 , GET_FROM_GALLERY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
                finish();
            // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {

        NestedScrollView nsv = (NestedScrollView) findViewById(R.id.nsv);
        nsv.setFillViewport(true);
        iv_food = (ImageView)findViewById(R.id.iv_food);
        fabB = (FloatingActionButton) findViewById(R.id.fab);
        fabB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSubmitClick();
            }
        });
        fabB.setImageResource(R.drawable.ic_arrow_forward);
        iv_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleImageClick(view);
            }
        });


        til_name = (TextInputLayout) findViewById(R.id.til_food_name);
        til_desc = (TextInputLayout) findViewById(R.id.til_food_desc);
        til_type = (TextInputLayout) findViewById(R.id.til_food_type);
        til_category = (TextInputLayout) findViewById(R.id.til_food_category);
        til_pref_routines = (TextInputLayout) findViewById(R.id.til_food_pref_routines);

        til_type.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTypeClick();
            }
        });

        til_category.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCategoryClick();
            }
        });
        til_pref_routines.setVisibility(GONE);
        til_pref_routines.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePrefRoutineClick();
            }
        });

        til_serving = (TextInputLayout) findViewById(R.id.til_food_serving);
        til_unit = (TextInputLayout) findViewById(R.id.til_food_unit);
        til_content = (TextInputLayout) findViewById(R.id.til_food_content);

        til_content.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateFoodContentHeaders();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        til_serving.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleServingClick();
            }
        });

        til_unit.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleUnitClick();
            }
        });
        til_calory = (TextInputLayout) findViewById(R.id.til_food_calory);
        til_fat = (TextInputLayout) findViewById(R.id.til_food_fat);
        til_protine = (TextInputLayout) findViewById(R.id.til_food_protine);
        til_carbs = (TextInputLayout) findViewById(R.id.til_food_carbs);
        til_fiber = (TextInputLayout) findViewById(R.id.til_food_fiber);

        til_calcium = (TextInputLayout) findViewById(R.id.til_food_calcium);
        til_sodium = (TextInputLayout) findViewById(R.id.til_food_sodium);
        til_potassium = (TextInputLayout) findViewById(R.id.til_food_potassium);
        til_iron = (TextInputLayout) findViewById(R.id.til_food_iron);
        til_zinc = (TextInputLayout) findViewById(R.id.til_food_zinc);
        til_magnisium = (TextInputLayout) findViewById(R.id.til_food_magnisium);

        til_vitaminA = (TextInputLayout) findViewById(R.id.til_food_vitaminA);
        til_vitaminB = (TextInputLayout) findViewById(R.id.til_food_vitaminB12);
        til_vitaminC = (TextInputLayout) findViewById(R.id.til_food_vitaminC);
        til_vitaminD = (TextInputLayout) findViewById(R.id.til_food_vitaminD);
        til_vitaminE = (TextInputLayout) findViewById(R.id.til_food_vitaminE);
        til_vitaminK = (TextInputLayout) findViewById(R.id.til_food_vitaminK);

        til_cholestrol = (TextInputLayout) findViewById(R.id.til_food_cholestrol);
        til_sugar = (TextInputLayout) findViewById(R.id.til_food_sugar);

    }


    private void handlePrefRoutineClick() {
        new MaterialDialog.Builder(this)
                .title("Choose Preferred Routines")
                .items(Routines.getAllNames())
                .itemsCallbackMultiChoice(new Integer[]{-1}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        setFoodItemValues(til_pref_routines, text);
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
                        setFoodItemValue(til_type, text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void handleServingClick() {
        new MaterialDialog.Builder(this)
                .title("Select Food Serving")
                .items(Servings.getAllNames())
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        setFoodItemValue(til_serving, text);
                        updateFoodContentHeaders();
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void updateFoodContentHeaders() {
        TextView nutri = (TextView)findViewById(R.id.tv_nutients_header);
        TextView vit = (TextView)findViewById(R.id.tv_vitamins_heading);
        TextView min = (TextView)findViewById(R.id.tv_minerals_heading);
        TextView oth = (TextView)findViewById(R.id.tv_other_heading);

        String serving = "";
        String content = "";
        String unit = "";
        if(TextUtils.isEmpty(til_serving.getEditText().getText())){
            serving = "serving";
        } else {
            serving = til_serving.getEditText().getText().toString();
        }
        if(TextUtils.isEmpty(til_content.getEditText().getText())){
            content = "";
        } else {
            content = til_content.getEditText().getText().toString();
        }
        if(TextUtils.isEmpty(til_unit.getEditText().getText())){
            unit = "";
        } else {
            unit = til_unit.getEditText().getText().toString();
        }

        String n = "Nutrients";
        String v = "Vitamins";
        String m = "Minerals";
        String o = "Other Contents";
        if(TextUtils.isEmpty(serving)){
            n = n+" Per Serving";
            m = m+" Per Serving";
            v = v+" Per Serving";
            o = o+" Per Serving";
        } else {
            n = n+" Per "+serving;
            m = m+" Per "+serving;
            v = v+" Per "+serving;
            o = o+" Per "+serving;
        }

        if(!TextUtils.isEmpty(content) && !TextUtils.isEmpty(unit)){
            n = n+" ("+content+" "+unit+")";
            m = m+" ("+content+" "+unit+")";
            v = v+" ("+content+" "+unit+")";
            o = o+" ("+content+" "+unit+")";
        } else {

        }

        nutri.setText(n);
        min.setText(m);
        vit.setText(v);
        oth.setText(o);
    }

    private void handleUnitClick() {
        new MaterialDialog.Builder(this)
                .title("Select Content Unit")
                .items(Units.getAllNames())
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        setFoodItemValue(til_unit, text);
                        updateFoodContentHeaders();
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
                        setFoodItemValue(til_category, text);
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void setFoodItemValues(TextInputLayout vg, CharSequence[] values) {
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

    private void setFoodItemValue(TextInputLayout vg, CharSequence text) {
        vg.getEditText().setText(text);
    }

    private void handleSubmitClick() {
            if(mode == VIEW) {
                changeMode(EDIT);
                return;
            }
            if (!isValidated()) {
                return;
            }

            Food food = getFood();

            food.setName(til_name.getEditText().getText().toString());
            food.setDesc(til_desc.getEditText().getText().toString());
            food.setType(FoodType.getIdByName(til_type.getEditText().getText().toString()));
            food.setCategory(FoodCategory.getIdByName(til_category.getEditText().getText().toString()));
            food.setCategory(FoodCategory.getIdByName(til_category.getEditText().getText().toString()));

/*            String prefRoutines = til_pref_routines.getEditText().getText().toString();
            String[] routines = prefRoutines.split(",");
            List<Integer> pRoutines = new ArrayList<>();
            for (String routine : routines) {
                int id = Routines.getIdByName(routine);
                pRoutines.add(id);
            }
            food.setPrefRoutine(pRoutines);*/


            food.setServingId(Servings.getIdByName(til_serving.getEditText().getText().toString()));
            food.setUnitId(Units.getIdByName(til_unit.getEditText().getText().toString()));
            food.setContentPerServing(Integer.parseInt(til_content.getEditText().getText().toString()));


            food.setCalory(Float.parseFloat(til_calory.getEditText().getText().toString()));
            food.setFat(Float.parseFloat(til_fat.getEditText().getText().toString()));
            food.setCarbs(Float.parseFloat(til_carbs.getEditText().getText().toString()));
            food.setProtine(Float.parseFloat(til_protine.getEditText().getText().toString()));


            if (!TextUtils.isEmpty(til_fiber.getEditText().getText()))
                food.setFiber(Float.parseFloat(til_fiber.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_potassium.getEditText().getText()))
                food.setPotassium(Float.parseFloat(til_potassium.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_sodium.getEditText().getText()))
                food.setSodium(Float.parseFloat(til_sodium.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_calcium.getEditText().getText()))
                food.setCalcium(Float.parseFloat(til_calcium.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_iron.getEditText().getText()))
                food.setIron(Float.parseFloat(til_iron.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_zinc.getEditText().getText()))
                food.setZinc(Float.parseFloat(til_zinc.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_magnisium.getEditText().getText()))
                food.setMagnissium(Float.parseFloat(til_magnisium.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_vitaminA.getEditText().getText()))
                food.setVitaminA(Float.parseFloat(til_vitaminA.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_vitaminB.getEditText().getText()))
                food.setVitaminB(Float.parseFloat(til_vitaminB.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_vitaminC.getEditText().getText()))
                food.setVitaminC(Float.parseFloat(til_vitaminC.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_vitaminD.getEditText().getText()))
                food.setVitaminD(Float.parseFloat(til_vitaminD.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_vitaminE.getEditText().getText()))
                food.setVitaminE(Float.parseFloat(til_vitaminE.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_vitaminK.getEditText().getText()))
                food.setVitaminK(Float.parseFloat(til_vitaminK.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_cholestrol.getEditText().getText()))
                food.setCholestrol(Float.parseFloat(til_cholestrol.getEditText().getText().toString()));

            if (!TextUtils.isEmpty(til_sugar.getEditText().getText()))
                food.setSugar(Float.parseFloat(til_sugar.getEditText().getText().toString()));

            food.setBitmap(foodBitmap);
            if(TextUtils.isEmpty(food.getCreator()))
            food.setCreator(LoginUtils.getUserCredential());
            CreateFoodUseCase usecase = (CreateFoodUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.CREATE_FOOD);
            usecase.execute(this, food, true);

    }

    private boolean isValidated() {

            if (TextUtils.isEmpty(til_name.getEditText().getText())) {
                Toast.makeText(this, "Please enter food name", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(til_desc.getEditText().getText())) {
                Toast.makeText(this, "Please enter food description", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(til_type.getEditText().getText())) {
                Toast.makeText(this, "Please select food type", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(til_category.getEditText().getText())) {
                Toast.makeText(this, "Please select food category", Toast.LENGTH_SHORT).show();
                return false;
            }
            /*if (TextUtils.isEmpty(til_pref_routines.getEditText().getText())) {
                Toast.makeText(this, "Please select preferred routines", Toast.LENGTH_SHORT).show();
                return false;
            }*/

            if (TextUtils.isEmpty(til_serving.getEditText().getText())) {
                Toast.makeText(this, "Please select food serving", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(til_unit.getEditText().getText())) {
                Toast.makeText(this, "Please select food unit", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(til_content.getEditText().getText())) {
                Toast.makeText(this, "Please select content per serving", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (TextUtils.isEmpty(til_calory.getEditText().getText())) {
                Toast.makeText(this, "Please enter calory", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (TextUtils.isEmpty(til_fat.getEditText().getText())) {
                Toast.makeText(this, "Please enter fat", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(til_carbs.getEditText().getText())) {
                Toast.makeText(this, "Please enter carbohydrates", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(til_protine.getEditText().getText())) {
                Toast.makeText(this, "Please enter protine", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
    }


    public static Intent getCallingIntent(Activity context, long mealId, long foodId,LaunchMode mode) {
        Intent i = new Intent(context, CreateFoodActivity.class);
        i.putExtra("mealId", mealId);
        i.putExtra("foodId", foodId);
        i.putExtra("mode", mode.code);
        return i;
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onBlobFetched(String blobKey, Bitmap bitmap) {
        if(bitmap != null)
            iv_food.setImageBitmap(bitmap);
    }

    @Override
    public void onFoodListFetched(List<Food> foods, String searchKey) {
        setFood(foods.get(0));
        loadUIwithFood();
    }

    private void loadUIwithFood() {
            loadFoodImage();

            til_name.getEditText().setText(getFood().getName());
            til_desc.getEditText().setText(getFood().getDesc());
            til_type.getEditText().setText(FoodType.getById(getFood().getType()).lable);
            til_category.getEditText().setText(FoodCategory.getById(getFood().getCategory()).lable);

            til_serving.getEditText().setText(Servings.getById(getFood().getServingId()).lable);
            til_content.getEditText().setText("" + getFood().getContentPerServing());
            til_unit.getEditText().setText(Units.getById(getFood().getUnitId()).lable);

            til_calory.getEditText().setText("" + getFood().getCalory());
            til_fat.getEditText().setText("" + getFood().getFat());
            til_protine.getEditText().setText("" + getFood().getProtine());
            til_carbs.getEditText().setText("" + getFood().getCarbs());
            til_fiber.getEditText().setText("" + getFood().getFiber());

            til_calcium.getEditText().setText("" + getFood().getCalcium());
            til_sodium.getEditText().setText("" + getFood().getSodium());
            til_potassium.getEditText().setText("" + getFood().getPotassium());
            til_iron.getEditText().setText("" + getFood().getIron());
            til_zinc.getEditText().setText("" + getFood().getZinc());
            til_magnisium.getEditText().setText("" + getFood().getMagnissium());

            til_vitaminA.getEditText().setText("" + getFood().getVitaminA());
            til_vitaminB.getEditText().setText("" + getFood().getVitaminB());
            til_vitaminC.getEditText().setText("" + getFood().getVitaminC());
            til_vitaminD.getEditText().setText("" + getFood().getVitaminD());
            til_vitaminE.getEditText().setText("" + getFood().getVitaminE());
            til_vitaminK.getEditText().setText("" + getFood().getVitaminK());

            til_cholestrol.getEditText().setText("" + getFood().getCholestrol());
            til_sugar.getEditText().setText("" + getFood().getSugar());

        updateFoodContentHeaders();
        //fetchFoodWithIds(CommonUtils.getLongArrayFromList(getMeal().getFoodIds()));

    }

    private void loadFoodImage() {
        FetchBlobUseCase usecase = (FetchBlobUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_BLOB);
        usecase.execute(this,false,getFood().getBlobKey(),getFood().getBlobServingUrl());
    }

    public void setFood(Food food) {
        this.food = food;
        collapsingToolBar.setTitle(food.getName());
    }

    public Food getFood() {
        if(food == null){
            food = new Food();
        }
        return food;
    }
    @Override
    public void onFoodCreated(Food food) {

        if (food != null && mode == CREATE) {
            foodId = food.getUid();
            Intent i = new Intent();
            i.putExtra("foodId", food.getUid());
            food.setBitmap(null);
            i.putExtra("food", Parcels.wrap(food));
            i.putExtra("mealId", mealId);
            setResult(RESULT_OK, i);
            finish();
        } else if (food != null && mode == EDIT) {
            changeMode(LaunchMode.VIEW);
            setFood(food);
            loadUIwithFood();
        }
    }

    private void changeMode(LaunchMode mode) {
        this.mode = mode;
        if(mode == VIEW){
            fabB.setImageResource(R.drawable.ic_mode_edit);
            updateEditability(false);
        } else if(mode == EDIT){
            fabB.setImageResource(R.drawable.ic_check);
            updateEditability(true);
        } else if(mode == CREATE) {
            fabB.setImageResource(R.drawable.ic_check);
            updateEditability(true);
        }

    }

    private void updateEditability(boolean isEditable) {
            if(til_name == null) return;
            til_name.getEditText().setEnabled(isEditable);
            til_desc.getEditText().setEnabled(isEditable);
            til_type.getEditText().setEnabled(isEditable);
            til_category.getEditText().setEnabled(isEditable);
            iv_food.setClickable(isEditable);
            if(til_serving == null) return;
            til_serving.getEditText().setEnabled(isEditable);
            til_content.getEditText().setEnabled(isEditable);
            til_unit.getEditText().setEnabled(isEditable);
            if(til_calory == null) return;
            til_calory.getEditText().setEnabled(isEditable);
            til_protine.getEditText().setEnabled(isEditable);
            til_fiber.getEditText().setEnabled(isEditable);
            til_fat.getEditText().setEnabled(isEditable);
            til_carbs.getEditText().setEnabled(isEditable);

            til_calcium.getEditText().setEnabled(isEditable);
            til_potassium.getEditText().setEnabled(isEditable);
            til_magnisium.getEditText().setEnabled(isEditable);
            til_iron.getEditText().setEnabled(isEditable);
            til_zinc.getEditText().setEnabled(isEditable);
            til_sodium.getEditText().setEnabled(isEditable);

            til_vitaminA.getEditText().setEnabled(isEditable);
            til_vitaminB.getEditText().setEnabled(isEditable);
            til_vitaminC.getEditText().setEnabled(isEditable);
            til_vitaminD.getEditText().setEnabled(isEditable);
            til_vitaminE.getEditText().setEnabled(isEditable);
            til_vitaminK.getEditText().setEnabled(isEditable);

            til_sugar.getEditText().setEnabled(isEditable);
            til_cholestrol.getEditText().setEnabled(isEditable);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode== GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                iv_food.setImageBitmap(bitmap);
                foodBitmap = bitmap;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
