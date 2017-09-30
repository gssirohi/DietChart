package com.techticz.app.ui.customview;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.techticz.app.R;
import com.techticz.app.domain.interactor.FetchImageInteractor;

import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.ui.activity.DailyRoutineActivity;
import com.techticz.app.ui.viewmodel.contract.IMealRoutineViewModel;
import com.techticz.app.utility.AppNavigator;
import com.techticz.app.utility.AppUtils;


/**
 * TODO: document your custom view class.
 */

public class MealRoutineView extends FrameLayout {
    private final int day;
    private MealRoutine viewModel;
    private FetchImageInteractor interactor;
    private MealView mealView;

    public MealRoutineView(int tab, ViewGroup parent) {
        super(parent.getContext());
        this.day = tab;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        init(parent);

    }


    private void init(ViewGroup parent) {
        ViewGroup itemView =
                (ViewGroup) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.meal_routine_layout, parent, false);
        itemView.findViewById(R.id.ll_status).setVisibility(GONE);
        addView(itemView);
    }

    public void fillDetails(MealRoutine viewModel) {
        this.viewModel = viewModel;
        TextView routineName = (TextView) findViewById(R.id.tv_routine_name);
        TextView routineDesc = (TextView) findViewById(R.id.tv_routine_desc);
        ImageView iv_clear  = (ImageView)findViewById(R.id.iv_remove);
        ViewGroup vg = (ViewGroup) findViewById(R.id.ll_routine_bg);
        LinearLayout mealContainer = (LinearLayout) findViewById(R.id.ll_meal_container);
        LinearLayout addMeal = (LinearLayout) findViewById(R.id.ll_add_meal);
        mealContainer.removeAllViews();

        routineName.setText(viewModel.getName());
        routineDesc.setText(viewModel.getDesc());
        iv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRemoveClick();
            }
        });
/*
        if (viewModel.getName().contains("Breakfast")) {
            vg.setBackgroundResource(R.drawable.bg_card_13);
        } else if (viewModel.getName().contains("Lunch")) {
            vg.setBackgroundResource(R.drawable.bg_card_8);
        } else if (viewModel.getName().contains("Snacks")) {
            vg.setBackgroundResource(R.drawable.bg_card_11);
        } else if (viewModel.getName().contains("Dinner")) {
            vg.setBackgroundResource(R.drawable.bg_card_7);
        }*/
        vg.setBackgroundResource(R.drawable.bg_card_7);
        if (viewModel.getMeal() != null) {
            addMeal.setVisibility(GONE);
            iv_clear.setVisibility(VISIBLE);
            mealView = new MealView(getContext());
            mealContainer.addView(mealView);

            mealView.fillDetails(viewModel.getMeal());
        } else {
            mealContainer.removeAllViews();
            addMeal.setVisibility(VISIBLE);
            iv_clear.setVisibility(GONE);
            addMeal.findViewById(R.id.b_add_meal).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAddMealClick();
                }
            });
        }

    }

    public MealView getMealView() {
        return mealView;
    }

    private void handleRemoveClick() {
            (new MaterialDialog.Builder(getContext()))
                    .title("Remove Meal")
                    .content("Would you really like to remove this meal from the routine?")
                    .positiveText("Remove")
                    .negativeText("Cancel")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            removeMealFromRoutine();
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

    private void removeMealFromRoutine() {
        ((DailyRoutineActivity)getContext()).removeMealFromRoutine(day,viewModel.getUid(),viewModel.getMeal());
    }

    private void onAddMealClick() {
        //AppNavigator.startAddMealScreen((Activity)getContext(),viewModel.getId(),viewModel.getName(), day, AppUtils.getDayFullName(day));
        MealPlan plan = ((DailyRoutineActivity) getContext()).getMealPlan();
        AppNavigator.startBrowseMealActivity((Activity) getContext(), plan.getUid(), day, viewModel.getUid());
    }
    public void setStatusActionVisibility(int visibility){
        findViewById(R.id.ll_status).setVisibility(visibility);
    }
    public MealRoutine getViewModel() {
        return viewModel;
    }
}
