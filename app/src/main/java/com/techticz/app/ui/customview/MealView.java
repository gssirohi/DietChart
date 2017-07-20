package com.techticz.app.ui.customview;

import android.content.Context;
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
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.FoodType;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.FetchImageInteractor;
import com.techticz.app.domain.interactor.LoadImageUseCase;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.Nutrition;

import com.techticz.app.ui.viewmodel.contract.IMealRoutineViewModel;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;

import java.util.List;


/**
 * TODO: document your custom view class.
 */

public class MealView extends FrameLayout {
    private Meal viewModel;
    private FetchImageInteractor interactor;

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
        TextView tv_nutri_label_1 = (TextView) findViewById(R.id.tv_nutri_label_1);
        TextView tv_nutri_label_2 = (TextView) findViewById(R.id.tv_nutri_label_2);
        TextView tv_nutri_label_3 = (TextView) findViewById(R.id.tv_nutri_label_3);
        TextView tv_nutri_label_4 = (TextView) findViewById(R.id.tv_nutri_label_4);

        TextView tv_nutri_1 = (TextView) findViewById(R.id.tv_nutri_1);
        TextView tv_nutri_2 = (TextView) findViewById(R.id.tv_nutri_2);
        TextView tv_nutri_3 = (TextView) findViewById(R.id.tv_nutri_3);
        TextView tv_nutri_4 = (TextView) findViewById(R.id.tv_nutri_4);

        ImageView imageView = (ImageView) findViewById(R.id.iv_meal_image);

        name.setText(viewModel.getName());
        desc.setText(viewModel.getDesc());
        type.setText(FoodType.getById(viewModel.getType()).lable);

/*
        String url = viewModel.getImageUrl();
        imageView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        if (interactor != null)
            interactor.setCancelled(true);//discard previous results since you have new  imageUyrl
        interactor = (FetchImageInteractor) AppCore.getInstance().getProvider().getUseCaseImpl(getContext(), UseCases.FETCH_PRODUCT_IMAGE);
        interactor.execute(new LoadImageUseCase.Callback() {
            @Override
            public void onError(AppErrors error) {

            }

            @Override
            public void onImage(Bitmap image) {
                imageView.setImageBitmap(image);
                imageView.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        }, url, false);*/
    }

    public Meal getViewModel() {
        return viewModel;
    }
}