package com.techticz.app.ui.customview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.FoodType;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.FetchBlobUseCase;
import com.techticz.app.domain.interactor.FetchImageInteractor;
import com.techticz.app.domain.interactor.IExtractNutritientUseCase;
import com.techticz.app.domain.interactor.LoadImageUseCase;
import com.techticz.app.domain.model.pojo.AddedFood;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.NutitionInfo;
import com.techticz.app.domain.model.pojo.Nutrition;

import com.techticz.app.ui.activity.DailyRoutineActivity;
import com.techticz.app.ui.viewmodel.contract.IMealRoutineViewModel;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;
import com.techticz.app.utility.AppNavigator;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * TODO: document your custom view class.
 */

public class MealView extends FrameLayout implements FetchBlobUseCase.Callback, IExtractNutritientUseCase.Callback {
    private Meal viewModel;
    private FetchImageInteractor interactor;
    private ImageView imageView;
    private Bitmap mealBitmap;

    public MealView(Context context) {
        super(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        init();

    }


    private void init() {
        ViewGroup itemView =
                (ViewGroup) LayoutInflater.from(getContext())
                        .inflate(R.layout.meal_layout, null, false);
        addView(itemView);
    }


    public void fillDetails(Meal viewModel) {
        this.viewModel = viewModel;

        TextView name = (TextView) findViewById(R.id.tv_meal_name);
        TextView desc = (TextView) findViewById(R.id.tv_meal_desc);
        TextView type = (TextView) findViewById(R.id.tv_meal_type);
        TextView explore = (TextView) findViewById(R.id.tv_meal_explore);

        imageView = (ImageView) findViewById(R.id.iv_meal_image);

        name.setText(viewModel.getName());
        desc.setText(viewModel.getDesc());
//        type.setText(FoodType.getById(viewModel.getType()).lable);

        explore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleExploreClick();
            }
        });
        calculateMealNutritions();
     loadMealImage();
    }

    private void calculateMealNutritions() {
        IExtractNutritientUseCase usecase = (IExtractNutritientUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(getContext(), UseCases.CALCULATE_NUTRI);
        usecase.execute(this,false,viewModel,null,null);
    }

    private void updateMealNutritionInfo(NutitionInfo totalN) {
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll_nutritient_view_container);
        List<Nutri> nutriList = new ArrayList<>();
        nutriList.add(new Nutri("Calory",totalN.getCalory()));
        nutriList.add(new Nutri("Carbs",totalN.getCarbs()));
        nutriList.add(new Nutri("Fat",totalN.getFat()));
        nutriList.add(new Nutri("Protine",totalN.getProtine()));
       // nutriList.add(new Nutri("Fiber",totalN.getFiber()));
        ViewGroup f1 = (ViewGroup) ll.findViewById(R.id.label_value_calory);
        ViewGroup f2 = (ViewGroup) ll.findViewById(R.id.label_value_carbs);
        ViewGroup f3 = (ViewGroup) ll.findViewById(R.id.label_value_fat);
        ViewGroup f4 = (ViewGroup) ll.findViewById(R.id.label_value_protine);
        List<ViewGroup> ff = new ArrayList<>();
        ff.add(0,f1);ff.add(1,f2);ff.add(2,f3);ff.add(3,f4);
        int i = 0;
        for(Nutri n: nutriList){
            ((TextView)(ff.get(i).findViewById(R.id.tv_label))).setText(n.label);
            ((TextView)(ff.get(i).findViewById(R.id.tv_value))).setText(""+n.value);
            i++;
        }
    }

    public class Nutri{
        String label;
        float value;
        String unit;

        public Nutri(String label, float value) {
            this.label = label;
            this.value = value;
        }
    }

    private void loadMealImage() {
        if(mealBitmap == null) {
            FetchBlobUseCase usecase = (FetchBlobUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(getContext(), UseCases.FETCH_BLOB);
            usecase.execute(this, false, viewModel.getBlobKey(), viewModel.getBlobServingUrl());
        } else {
            imageView.setImageBitmap(mealBitmap);
        }
    }

    private void handleExploreClick() {
        SharedPreferences pref = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        long planId = pref.getLong("plan", 0);
        ((BaseActivity)getContext()).getNavigator()
                .navigateToMealDetailsActivity((BaseActivity)getContext(),planId,viewModel.getUid());
    }

    public Meal getViewModel() {
        return viewModel;
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onNutritionFetched(NutitionInfo meal, NutitionInfo dayMeals, NutitionInfo plan) {
        updateMealNutritionInfo(meal);
    }

    @Override
    public void onBlobFetched(String blobKey, Bitmap bitmap) {
        if(bitmap != null) {
            this.mealBitmap = bitmap;
            imageView.setImageBitmap(bitmap);
        }
    }
}
