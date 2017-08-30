package com.techticz.app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.techticz.app.constant.LaunchMode;
import com.techticz.app.constant.Routines;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.CreateMealUseCase;
import com.techticz.app.domain.interactor.FetchBlobUseCase;
import com.techticz.app.domain.interactor.FetchFoodListUseCase;
import com.techticz.app.domain.interactor.FetchMealDetailsUseCase;
import com.techticz.app.domain.model.pojo.AddedFood;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.NutitionInfo;
import com.techticz.app.ui.customview.CircleItemView;
import com.techticz.app.ui.customview.FoodListItemView;

import org.parceler.Parcels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static android.view.View.GONE;
import static com.techticz.app.constant.Key.GET_FROM_GALLERY;
import static com.techticz.app.constant.LaunchMode.CREATE;
import static com.techticz.app.constant.LaunchMode.EDIT;
import static com.techticz.app.constant.LaunchMode.VIEW;

public class CreateMealActivity extends BaseActivity implements  CreateMealUseCase.Callback
        ,  FetchBlobUseCase.Callback
         ,FoodListItemView.SelectionListner, FetchMealDetailsUseCase.Callback {

    private TextInputLayout til_meal_name;
    private TextInputLayout til_meal_desc;
    private TextInputLayout til_meal_category;
    private TextInputLayout til_meal_type;
    private TextInputLayout til_meal_pref_routine;
    private Button b_add_food;
    private LinearLayout ll_food_container;
    private LinearLayout ll_nutri_container;
    private long mealPlanId;
    private long routineId;
    private int day;
    private long mealId;
    private FetchFoodListUseCase fetchFoodUseCase;
    private Meal meal;
    private ImageView iv_meal;
    private Bitmap mealBitmap;
    private FloatingActionButton fab_meal;
    private LaunchMode mode;
    private boolean imageUploadRequired;
    private Button b_remove_food;
    private CollapsingToolbarLayout collapsingToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolBar = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        collapsingToolBar.setTitle("Loading Meal");

        fab_meal = (FloatingActionButton) findViewById(R.id.fab);
        fab_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSubmitClick();
            }
        });
        initData();
        initUI();
        provideData();
    }

    private void provideData() {
        if(mealId != 0){
           // FetchMealListUseCase usecase = (FetchMealListUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_MEAL_LIST);
            FetchMealDetailsUseCase usecase = (FetchMealDetailsUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_MEAL_DETAILS);
            usecase.execute(this,true,mealId);
        } else {
            collapsingToolBar.setTitle("Create Meal");
        }
    }

    private void initData() {
        mealPlanId = getIntent().getLongExtra("mealPlanId", 0);
        mealId = getIntent().getLongExtra("mealId", 0);
        routineId = getIntent().getIntExtra("routineId", 0);
        mode = LaunchMode.getById(getIntent().getIntExtra("mode",0));
        day = getIntent().getIntExtra("day", -1);
    }

    private void initUI() {
        til_meal_name = (TextInputLayout) findViewById(R.id.til_meal_name);
        til_meal_desc = (TextInputLayout) findViewById(R.id.til_meal_desc);
        til_meal_category = (TextInputLayout) findViewById(R.id.til_meal_category);
        til_meal_type = (TextInputLayout) findViewById(R.id.til_meal_type);
        til_meal_pref_routine = (TextInputLayout) findViewById(R.id.til_meal_pref_routines);
        b_add_food = (Button) findViewById(R.id.b_meal_add_food);
        b_remove_food = (Button) findViewById(R.id.b_meal_remove_food);
        ll_food_container = (LinearLayout) findViewById(R.id.ll_food_item);
        ll_nutri_container = (LinearLayout) findViewById(R.id.ll_nutritient_view_container);
        iv_meal = (ImageView)findViewById(R.id.iv_meal);
        iv_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleImageClick(view);
            }
        });

        b_remove_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRemoveFoodClick();
            }
        });
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
        changeMode(mode);
    }

    private void handleRemoveFoodClick() {
        int children = ll_food_container.getChildCount();
        List<Food> foods = new ArrayList<>();
        ArrayList<AddedFood> aFoods = new ArrayList<>();
        for (int i = 0; i < children; i++) {
            FoodListItemView view = (FoodListItemView) ll_food_container.getChildAt(i);
            if(!view.isFoodSelected()){
                Food food = view.getViewModel();
                foods.add(food);
                long uid = food.getUid();
                int serving  = view.getServing();
                aFoods.add(new AddedFood(food,serving));
            }
        }

        getMeal().setAddedFoods(aFoods);
        //getMeal().setFoodIds();
        ll_food_container.removeAllViews();
        for(int i = 0; i<foods.size();i++){
            FoodListItemView view = new FoodListItemView(this);
            view.fillDetails(foods.get(i),getServingForFood(foods.get(i).getUid()));
            if(mode == EDIT || mode == CREATE){
                view.displayCheckBox(true);
                view.servingEdit(true);
            }
            ll_food_container.addView(view);
        }
        updateRemoveButtonVisibility();
        calculateMealNutritions();
    }

    private void changeMode(LaunchMode mode) {
        this.mode = mode;
        if(mode == VIEW){
            fab_meal.setImageResource(R.drawable.ic_mode_edit);
            updateEditability(false);
        } else if(mode == EDIT){
            fab_meal.setImageResource(R.drawable.ic_check);
            updateEditability(true);
        } else if(mode == CREATE) {
            fab_meal.setImageResource(R.drawable.ic_check);
            updateEditability(true);
        }

    }

    private void handleImageClick(View view) {
        if(mode == EDIT || mode == CREATE) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    , GET_FROM_GALLERY);
        }
    }

    private void updateEditability(boolean isEditable) {
        til_meal_name.getEditText().setEnabled(isEditable);
        til_meal_desc.getEditText().setEnabled(isEditable);
        til_meal_type.getEditText().setEnabled(isEditable);
        til_meal_category.getEditText().setEnabled(isEditable);
        til_meal_pref_routine.getEditText().setEnabled(isEditable);
        b_add_food.setVisibility(isEditable?View.VISIBLE:GONE);
        iv_meal.setClickable(isEditable);
        int children = ll_food_container.getChildCount();
        List<Long> foods = new ArrayList<>();
        for (int i = 0; i < children; i++) {
            FoodListItemView view = (FoodListItemView) ll_food_container.getChildAt(i);
            if(isEditable){
                view.displayCheckBox(true);
                view.servingEdit(true);
            }
            Food food = view.getViewModel();
            long uid = food.getUid();
            foods.add(uid);
        }
        updateRemoveButtonVisibility();
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

        if(mode == VIEW) {
            changeMode(EDIT);
            return;
        }

        if (!isvalidated()) {
            return;
        }


        getMeal().setName(til_meal_name.getEditText().getText().toString());
        getMeal().setDesc(til_meal_desc.getEditText().getText().toString());
        getMeal().setType(FoodType.getIdByName(til_meal_type.getEditText().getText().toString()));
        getMeal().setCategory(FoodCategory.getIdByName(til_meal_category.getEditText().getText().toString()));
        String prefRoutines = til_meal_pref_routine.getEditText().getText().toString();
        String[] routines = prefRoutines.split(",");
        List<Integer> pRoutines = new ArrayList<>();
        for (String routine : routines) {
            int id = Routines.getIdByName(routine);
            pRoutines.add(id);
        }
        getMeal().setPrefRoutine(pRoutines);

        int children = ll_food_container.getChildCount();
        List<Long> foods = new ArrayList<>();
        List<AddedFood> aFoods = new ArrayList<>();
        for (int i = 0; i < children; i++) {
            FoodListItemView view = (FoodListItemView) ll_food_container.getChildAt(i);
            Food food = view.getViewModel();
            long uid = food.getUid();
            int serving  = view.getServing();
            foods.add(uid);
            aFoods.add(new AddedFood(food,serving));
        }
        getMeal().setFoodIds(foods);
        getMeal().setAddedFoods(aFoods);
        getMeal().setBitmap(mealBitmap);
        if(imageUploadRequired){
            getMeal().setBlobServingUrl("");
            getMeal().setBlobKey("");
        }
        CreateMealUseCase usecase = (CreateMealUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.CREATE_AND_ADD_MEAL);
        usecase.execute(this, getMeal(), true);
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
            Parcelable parcel = data.getParcelableExtra("food");
            Food food = null;
            if(parcel != null)
            food = Parcels.unwrap(parcel);
            if(food != null){
                getMeal().getAddedFoods().add(new AddedFood(food));
                displayFood(food);
                calculateMealNutritions();
            }
        } else if(requestCode== GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                iv_meal.setImageBitmap(bitmap);
                mealBitmap = bitmap;
                imageUploadRequired = true;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    public static Intent getCallingIntent(Activity context, long mealPlanId,LaunchMode mode) {
        Intent i = new Intent(context, CreateMealActivity.class);
        i.putExtra("mealPlanId", mealPlanId);
        i.putExtra("mode", mode.code);
        return i;
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onMealDetailsFetched(Meal meal) {
        setMeal(meal);
        loadUIwithMeal();
    }

    @Override
    public void onBlobFetched(String blobKey, Bitmap bitmap) {
        if(bitmap != null)
        iv_meal.setImageBitmap(bitmap);
    }

    private void loadUIwithMeal() {
        til_meal_name.getEditText().setText(getMeal().getName());
        til_meal_desc.getEditText().setText(getMeal().getDesc());
        til_meal_type.getEditText().setText(FoodType.getById(getMeal().getType()).lable);
        til_meal_category.getEditText().setText(FoodCategory.getById(getMeal().getType()).lable);
        setPrefRoutinesFromCode(til_meal_pref_routine,getMeal().getPrefRoutine());
        clearFoodViews();
        displayAddedFoods(getMeal().getAddedFoods());
        calculateMealNutritions();
        //fetchFoodWithIds(CommonUtils.getLongArrayFromList(getMeal().getFoodIds()));
        loadMealImage();
    }

    private void calculateMealNutritions() {
        List<AddedFood> afs = getMeal().getAddedFoods();
        NutitionInfo totalN = new NutitionInfo();
        for(AddedFood af : afs){
            NutitionInfo nf = af.getFood().extractNutritions();
            totalN.add(nf);
        }

        updateMealNutritionInfo(totalN);
    }

    private void clearFoodViews() {
        ll_food_container.removeAllViews();
    }

    private void loadMealImage() {
        FetchBlobUseCase usecase = (FetchBlobUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.FETCH_BLOB);
        usecase.execute(this,false,getMeal().getBlobKey(),getMeal().getBlobServingUrl());
    }


    private void setPrefRoutinesFromCode(TextInputLayout til, List<Integer> prefRoutine) {
        if(prefRoutine == null || prefRoutine.isEmpty()) {
            til.getEditText().setText("");
            return;
        }
        CharSequence[] list = new CharSequence[prefRoutine.size()];
        int i = 0;
        for(Integer r: prefRoutine){
            Routines  rt = Routines.getById(r);
            if(rt != null){
                list[i++] = rt.lable;
            }
        }
        setMealItemValues(til,list);
    }

    @Override
    public void onMealCreated(Meal meal) {
        if (meal != null && mode == CREATE) {
            mealId = meal.getUid();
            Intent i = new Intent();
            i.putExtra("mealId", meal.getUid());
            meal.setBitmap(null);
            i.putExtra("meal", Parcels.wrap(meal));
            i.putExtra("mealPlanId", mealPlanId);
            i.putExtra("routineId", routineId);
            i.putExtra("day", day);
            setResult(RESULT_OK, i);
            finish();
        } else if (meal != null && mode == EDIT) {
            changeMode(LaunchMode.VIEW);
            setMeal(meal);
            loadUIwithMeal();
        }
    }

    public void displayFood(Food f) {
            FoodListItemView view = new FoodListItemView(this);
            view.fillDetails(f,getServingForFood(f.getUid()));
            if(mode == EDIT || mode == CREATE){
                view.displayCheckBox(true);
                view.servingEdit(true);
            }
            ll_food_container.addView(view);
        updateRemoveButtonVisibility();
    }

    public void displayAddedFoods(List<AddedFood> foods) {
        for(AddedFood f: foods) {
            FoodListItemView view = new FoodListItemView(this);
            view.fillDetails(f.getFood(),getServingForFood(f.getFoodId()));
            if(mode == EDIT || mode == CREATE){
                view.displayCheckBox(true);
                view.servingEdit(true);
            }
            ll_food_container.addView(view);
        }
        updateRemoveButtonVisibility();
    }

    private int getServingForFood(Long uid) {
        if(getMeal() == null) return 1;
        if(getMeal().getAddedFoods() == null) return 1;

        for(AddedFood ad: getMeal().getAddedFoods()){
            if(ad.getFoodId().longValue() == uid.longValue()){
                return ad.getServing();
            }
        }
        return 1;
    }

    public static Intent getCallingIntent(Activity context, long planId, long mealId,LaunchMode mode) {
        Intent i = new Intent(context, CreateMealActivity.class);
        i.putExtra("mealPlanId", planId);
        i.putExtra("mealId", mealId);
        i.putExtra("mode", mode.code);
        return i;
    }

    @Override
    public void onFoodSelectionChanged(long id,boolean b) {
        if(b){
            b_remove_food.setVisibility(View.VISIBLE);
        } else {
            updateRemoveButtonVisibility();
        }
    }

    private void updateRemoveButtonVisibility() {
        int children = ll_food_container.getChildCount();
        List<Long> foods = new ArrayList<>();
        for (int i = 0; i < children; i++) {
            FoodListItemView view = (FoodListItemView) ll_food_container.getChildAt(i);
            if(view.isFoodSelected()) {
                b_remove_food.setVisibility(View.VISIBLE);
                return;
            }
        }
        b_remove_food.setVisibility(GONE);
    }

    public void updateMealNutritionInfo(NutitionInfo info){
        ((CircleItemView)findViewById(R.id.civ_calory)).set("Calory",info.getCalory());
        ((CircleItemView)findViewById(R.id.civ_carbs)).set("Carbs",info.getCarbs());
        ((CircleItemView)findViewById(R.id.civ_fat)).set("Fat",info.getFat());
        ((CircleItemView)findViewById(R.id.civ_protine)).set("Protine",info.getProtine());
        ((CircleItemView)findViewById(R.id.civ_fiber)).set("Fiber",info.getFiber());

        ((CircleItemView)findViewById(R.id.civ_va)).set("Vitamin A",info.getVitaminA());
        ((CircleItemView)findViewById(R.id.civ_vb)).set("Vitamin B12",info.getVitaminB());
        ((CircleItemView)findViewById(R.id.civ_vc)).set("Vitamin C",info.getVitaminC());
        ((CircleItemView)findViewById(R.id.civ_vd)).set("Vitamin D",info.getVitaminD());
        ((CircleItemView)findViewById(R.id.civ_ve)).set("Vitamin E",info.getVitaminE());
        ((CircleItemView)findViewById(R.id.civ_vk)).set("Vitamin K",info.getVitaminK());

        ((CircleItemView)findViewById(R.id.civ_sodium)).set("Sodium",info.getSodium());
        ((CircleItemView)findViewById(R.id.civ_potasium)).set("Potassium",info.getPotassium());
        ((CircleItemView)findViewById(R.id.civ_magnisium)).set("Maganessium",info.getMagnissium());
        ((CircleItemView)findViewById(R.id.civ_iron)).set("Iron",info.getIron());
        ((CircleItemView)findViewById(R.id.civ_zinc)).set("Zinc",info.getZinc());
        ((CircleItemView)findViewById(R.id.civ_calcium)).set("Calcium",info.getCalcium());

        ((CircleItemView)findViewById(R.id.civ_sugar)).set("Sugar",info.getSugar());
        ((CircleItemView)findViewById(R.id.civ_cholestral)).set("Cholestrol",info.getCholestrol());
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
        collapsingToolBar.setTitle(meal.getName());
    }

    public Meal getMeal() {
        if(meal == null){
            meal = new Meal();
        }
        return meal;
    }
}
