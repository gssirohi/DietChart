package com.techticz.app.ui.customview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.FoodType;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.FetchImageInteractor;
import com.techticz.app.domain.interactor.IExtractNutritientUseCase;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.NutitionInfo;
import com.techticz.app.domain.model.pojo.Nutrition;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;
import com.tecticz.powerkit.ui.customview.RoundImageView;

import java.util.List;


/**
 * TODO: document your custom view class.
 */

public class MealListItemView extends FrameLayout implements IExtractNutritientUseCase.Callback {
    private Meal viewModel;
    private FetchImageInteractor interactor;
    private TextView tv_calories;

    public MealListItemView(Context context) {
        super(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        init();

    }


    private void init() {
        ViewGroup itemView =
                (ViewGroup) LayoutInflater.from(getContext())
                        .inflate(R.layout.sample_meal_list_item_view, null, false);
        addView(itemView);
        TextView tv_details = (TextView)findViewById(R.id.tv_details);
        tv_details.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDetailsClick();
            }
        });
    }

    public void fillDetails(Meal viewModel) {
        this.viewModel = viewModel;
        TextView name = (TextView) findViewById(R.id.tv_meal_name);
        TextView type = (TextView) findViewById(R.id.tv_meal_type);
        tv_calories = (TextView) findViewById(R.id.tv_calories);

        RoundImageView iv_meal = (RoundImageView) findViewById(R.id.iv_meal);

        name.setText(viewModel.getName());
        type.setText("contains "+viewModel.getAddedFoods().size()+" food items");
        tv_calories.setText(""+viewModel.getCalory());
        iv_meal.setUrl(viewModel.getBlobServingUrl());
        IExtractNutritientUseCase usecase = (IExtractNutritientUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(getContext(), UseCases.CALCULATE_NUTRI);
        usecase.execute(this,false,viewModel,null,null);
    }

    private void handleDetailsClick() {
        ((BaseActivity)getContext()).getNavigator().navigateToMealDetailsActivity((Activity) getContext(),0l,viewModel.getUid());
    }

    public Meal getViewModel() {
        return viewModel;
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onNutritionFetched(NutitionInfo meal, NutitionInfo dayMeals, NutitionInfo plan) {
        tv_calories.setText(""+meal.getCalory());
    }
}
