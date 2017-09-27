package com.techticz.app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dietchart.auth.utils.LoginUtils;
import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.LaunchMode;
import com.techticz.app.constant.Routines;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.MealPlanUseCase;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.tecticz.powerkit.ui.customview.AppImageView;

import org.parceler.Parcels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.techticz.app.constant.Key.GET_FROM_GALLERY;
import static com.techticz.app.constant.LaunchMode.CREATE;
import static com.techticz.app.constant.LaunchMode.CREATE_COPY;

public class CreateMealPlanActivity extends BaseActivity implements MealPlanUseCase.Callback {

    private AppImageView iv_meal_plan;
    private MealPlan mealPlan;
    private LaunchMode mode;
    private TextInputLayout til_name;
    private TextInputLayout til_desc;
    private TextInputLayout til_cal;
    private TextInputLayout til_auto_load;
    private CheckBox cb_auto_load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubmit();
            }
        });
        initData();
        initUiFields();
        loadUIWithPlan();
    }

    private void initUiFields() {
        iv_meal_plan = (AppImageView)findViewById(R.id.iv_meal_plan);
        til_name = (TextInputLayout) findViewById(R.id.til_plan_name);
        til_desc = (TextInputLayout) findViewById(R.id.til_plan_desc);
        til_cal = (TextInputLayout) findViewById(R.id.til_plan_calory);
        til_auto_load = (TextInputLayout) findViewById(R.id.til_auto_load_pref_routines);
        cb_auto_load = (CheckBox)findViewById(R.id.cb_auto_load);
        til_auto_load.setVisibility(View.GONE);
        cb_auto_load.setVisibility(View.GONE);
        if(mode == CREATE){
            cb_auto_load.setVisibility(View.VISIBLE);
        }
        List<Integer> pRoutines = new ArrayList<>();
        pRoutines.add(1);pRoutines.add(2);pRoutines.add(3);pRoutines.add(4);pRoutines.add(5);pRoutines.add(6);pRoutines.add(7);
        setPrefRoutinesFromCode(til_auto_load,pRoutines);
        cb_auto_load.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    til_auto_load.setVisibility(View.VISIBLE);
                } else {
                    til_auto_load.setVisibility(View.GONE);
                }
            }
        });
        iv_meal_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleImageClick(view);
            }
        });
        til_auto_load.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePrefRoutineClick();
            }
        });
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

    private void handlePrefRoutineClick() {
        new MaterialDialog.Builder(this)
                .title("Choose Preferred Routines")
                .items(Routines.getAllNames())
                .itemsCallbackMultiChoice(new Integer[]{-1}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        setMealItemValues(til_auto_load, text);
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

    private void initData() {
        int modeId = getIntent().getIntExtra("mode",LaunchMode.CREATE.code);
        this.mode = LaunchMode.getById(modeId);
        Parcelable planParcel = getIntent().getParcelableExtra("original");
        if(planParcel != null){
            this.mealPlan = Parcels.unwrap(planParcel);
            if(mode == CREATE_COPY){
                mealPlan.setUid(0l);
                mealPlan.setName("Copy-"+mealPlan.getName());
            }
        }


    }

    private void loadUIWithPlan() {
        til_name.getEditText().setText(getMealPlan().getName());
        til_desc.getEditText().setText(getMealPlan().getDesc());
        til_cal.getEditText().setText(""+getMealPlan().getDailyCalory());
        iv_meal_plan.setUrl(getMealPlan().getBlobServingUrl());
    }

    private void onClickSubmit() {
        if (!isValidated()) {
            return;
        }
        getMealPlan().setName(til_name.getEditText().getText().toString());
        getMealPlan().setDesc(til_desc.getEditText().getText().toString());
        if (!TextUtils.isEmpty(til_cal.getEditText().getText().toString())) {
            float cal = Float.parseFloat(til_cal.getEditText().getText().toString());
            getMealPlan().setDailyCalory(cal);
        }
        if(getMealPlan().getBitmap() == null){
            getMealPlan().setBitmap(iv_meal_plan.getBitmap());
        }
        if(TextUtils.isEmpty(getMealPlan().getCreator()))
            getMealPlan().setCreator(LoginUtils.getUserCredential());

        getMealPlan().setRecommended(true);
        List<Integer> pRoutines = new ArrayList<>();
        if(cb_auto_load.isChecked()) {
            String prefRoutines = til_auto_load.getEditText().getText().toString();
            String[] routines = prefRoutines.split(",");

            for (String routine : routines) {
                int id = Routines.getIdByName(routine);
                pRoutines.add(id);
            }
        } else {
            pRoutines.add(1);pRoutines.add(2);pRoutines.add(3);pRoutines.add(4);pRoutines.add(5);pRoutines.add(6);pRoutines.add(7);
        }
        MealPlanUseCase usecase = (MealPlanUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.CREATE_MEAL_PLAN);
        usecase.createMealPlan(this, getMealPlan(),cb_auto_load.isChecked(),pRoutines, true);

    }

    private MealPlan getMealPlan() {
        if(mealPlan == null){
            mealPlan = new MealPlan();
        }
        return mealPlan;
    }

    private void handleImageClick(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                , GET_FROM_GALLERY);
    }

    private boolean isValidated() {
        TextInputLayout til_name = (TextInputLayout) findViewById(R.id.til_plan_name);
        TextInputLayout til_desc = (TextInputLayout) findViewById(R.id.til_plan_desc);
        TextInputLayout til_cal = (TextInputLayout) findViewById(R.id.til_plan_calory);

        if (TextUtils.isEmpty(til_name.getEditText().getText())) {
            Toast.makeText(this, "Please enter plan name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(til_desc.getEditText().getText())) {
            Toast.makeText(this, "Please enter plan description", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public static Intent getCallingIntent(Context context) {
        Intent i = new Intent(context, CreateMealPlanActivity.class);
        i.putExtra("mode",CREATE.code);
        return i;
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onMealPlanCreated(MealPlan planWithId) {
        Toast.makeText(this, "Meal Plan :" + planWithId.getName() + " is created with ID:" + planWithId.getUid(), Toast.LENGTH_SHORT).show();
        long id = planWithId.getUid();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("plan", id);
        editor.commit();

        getNavigator().navigateToFoodChartActivity(planWithId.getUid());
        finish();
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
                iv_meal_plan.setImageBitmap(bitmap);
                getMealPlan().setBitmap(bitmap);
                getMealPlan().setBlobServingUrl("");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static Intent getCallingIntent(Context context, LaunchMode mode, MealPlan originalPlan) {
        Intent i = new Intent(context,CreateMealPlanActivity.class);
        i.putExtra("mode",mode.code);
        i.putExtra("original", Parcels.wrap(originalPlan));
        return i;
    }
}
