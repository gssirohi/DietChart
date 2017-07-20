package com.techticz.app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.FoodCategory;
import com.techticz.app.constant.FoodType;
import com.techticz.app.constant.Key;
import com.techticz.app.constant.Routines;
import com.techticz.app.constant.Servings;
import com.techticz.app.constant.Units;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.CreateFoodUseCase;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.ui.adapter.NewFoodPagerAdapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.techticz.app.constant.Key.GET_FROM_GALLERY;

public class CreateFoodActivity extends AppCompatActivity implements CreateFoodUseCase.Callback, NewFoodPagerAdapter.OnPageCreatedListner {

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
    private ViewPager pager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        iv_food = (ImageView)findViewById(R.id.iv_food);
        fabB = (FloatingActionButton) findViewById(R.id.fab);
        fabB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSubmitClick();
            }
        });

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        fabB.setImageResource(R.drawable.ic_arrow_forward);
        iv_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleImageClick(view);
            }
        });
    }

    private void handleImageClick(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                 , GET_FROM_GALLERY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            int index = pager.getCurrentItem();
            if (index == 2 || index == 1) {
                fabB.setImageResource(R.drawable.ic_arrow_forward);
            }
            if (index != 0) {
                pager.setCurrentItem(index - 1, true);
            } else {
                finish();
            }
            // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {

        NestedScrollView nsv = (NestedScrollView) findViewById(R.id.nsv);
        nsv.setFillViewport(true);

        pager = (ViewPager) findViewById(R.id.vp_new_food);

        pager.setAdapter(new NewFoodPagerAdapter(this, this));

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
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void handleUnitClick() {
        new MaterialDialog.Builder(this)
                .title("Select Content Unit")
                .items(Units.getAllNames())
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        setFoodItemValue(til_unit, text);
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

        int pageIndex = pager.getCurrentItem();

        if (pageIndex == 0) {
            if (isValidated(pageIndex)) {
                pager.setCurrentItem(1, true);
                fabB.setImageResource(R.drawable.ic_arrow_forward);
                return;
            }
        } else if (pageIndex == 1) {
            if (isValidated(pageIndex)) {
                pager.setCurrentItem(2, true);
                fabB.setImageResource(R.drawable.ic_check);
                return;
            }
        } else if (pageIndex == 2) {


            if (!isValidated(pageIndex)) {
                return;
            }

            Food food = new Food();

            food.setName(til_name.getEditText().getText().toString());
            food.setDesc(til_desc.getEditText().getText().toString());
            food.setType(FoodType.getIdByName(til_type.getEditText().getText().toString()));
            food.setCategory(FoodCategory.getIdByName(til_category.getEditText().getText().toString()));
            food.setCategory(FoodCategory.getIdByName(til_category.getEditText().getText().toString()));

            String prefRoutines = til_pref_routines.getEditText().getText().toString();
            String[] routines = prefRoutines.split(",");
            List<Integer> pRoutines = new ArrayList<>();
            for (String routine : routines) {
                int id = Routines.getIdByName(routine);
                pRoutines.add(id);
            }
            food.setPrefRoutine(pRoutines);


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
            CreateFoodUseCase usecase = (CreateFoodUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.CREATE_FOOD);
            usecase.execute(this, food, true);
        }
    }

    private boolean isValidated(int pageIndex) {
        if (pageIndex == 0) {
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
            if (TextUtils.isEmpty(til_pref_routines.getEditText().getText())) {
                Toast.makeText(this, "Please select preferred routines", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } else if (pageIndex == 1) {
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
            return true;
        } else if (pageIndex == 2) {
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
        return false;
    }

    public static Intent getCallingIntent(Activity context, int mealId) {
        Intent i = new Intent(context, CreateFoodActivity.class);

        i.putExtra("mealId", mealId);
        return i;

    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onFoodCreated(Food food) {
        Intent i = new Intent();
        i.putExtra("foodId", food.getUid());
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onPageCreated(ViewGroup view, int position) {

        if (position == 0) {
            til_name = (TextInputLayout) view.findViewById(R.id.til_food_name);
            til_desc = (TextInputLayout) view.findViewById(R.id.til_food_desc);
            til_type = (TextInputLayout) view.findViewById(R.id.til_food_type);
            til_category = (TextInputLayout) view.findViewById(R.id.til_food_category);
            til_pref_routines = (TextInputLayout) view.findViewById(R.id.til_food_pref_routines);

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
            til_pref_routines.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handlePrefRoutineClick();
                }
            });
        } else if (position == 1) {
            til_serving = (TextInputLayout) view.findViewById(R.id.til_food_serving);
            til_unit = (TextInputLayout) view.findViewById(R.id.til_food_unit);
            til_content = (TextInputLayout) view.findViewById(R.id.til_food_content);

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
        } else if (position == 2) {
            til_calory = (TextInputLayout) view.findViewById(R.id.til_food_calory);
            til_fat = (TextInputLayout) view.findViewById(R.id.til_food_fat);
            til_protine = (TextInputLayout) view.findViewById(R.id.til_food_protine);
            til_carbs = (TextInputLayout) view.findViewById(R.id.til_food_carbs);
            til_fiber = (TextInputLayout) view.findViewById(R.id.til_food_fiber);

            til_calcium = (TextInputLayout) view.findViewById(R.id.til_food_calcium);
            til_sodium = (TextInputLayout) view.findViewById(R.id.til_food_sodium);
            til_potassium = (TextInputLayout) view.findViewById(R.id.til_food_potassium);
            til_iron = (TextInputLayout) view.findViewById(R.id.til_food_iron);
            til_zinc = (TextInputLayout) view.findViewById(R.id.til_food_zinc);
            til_magnisium = (TextInputLayout) view.findViewById(R.id.til_food_magnisium);

            til_vitaminA = (TextInputLayout) view.findViewById(R.id.til_food_vitaminA);
            til_vitaminB = (TextInputLayout) view.findViewById(R.id.til_food_vitaminB12);
            til_vitaminC = (TextInputLayout) view.findViewById(R.id.til_food_vitaminC);
            til_vitaminD = (TextInputLayout) view.findViewById(R.id.til_food_vitaminD);
            til_vitaminE = (TextInputLayout) view.findViewById(R.id.til_food_vitaminE);
            til_vitaminK = (TextInputLayout) view.findViewById(R.id.til_food_vitaminK);

            til_cholestrol = (TextInputLayout) view.findViewById(R.id.til_food_cholestrol);
            til_sugar = (TextInputLayout) view.findViewById(R.id.til_food_sugar);
        }

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
