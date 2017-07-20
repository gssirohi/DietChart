package com.techticz.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.CreateMealPlanUseCase;
import com.techticz.app.domain.model.pojo.MealPlan;

public class CreateMealPlanActivity extends BaseActivity implements CreateMealPlanUseCase.Callback {

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
    }

    private void onClickSubmit() {
        if (!isValidated()) {
            return;
        }

        TextInputLayout til_name = (TextInputLayout) findViewById(R.id.til_plan_name);
        TextInputLayout til_desc = (TextInputLayout) findViewById(R.id.til_plan_desc);
        TextInputLayout til_cal = (TextInputLayout) findViewById(R.id.til_plan_calory);

        MealPlan plan = new MealPlan();
        plan.setName(til_name.getEditText().getText().toString());
        plan.setDesc(til_desc.getEditText().getText().toString());
        plan.setCreater("user");
        if (!TextUtils.isEmpty(til_cal.getEditText().getText().toString())) {
            float cal = Float.parseFloat(til_cal.getEditText().getText().toString());
            plan.setDailyCalory(cal);
        }

        CreateMealPlanUseCase usecase = (CreateMealPlanUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.CREATE_MEAL_PLAN);
        usecase.execute(this, plan, true);

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
}
