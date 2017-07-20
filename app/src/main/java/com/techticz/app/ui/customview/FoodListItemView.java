package com.techticz.app.ui.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.constant.FoodType;
import com.techticz.app.domain.interactor.FetchImageInteractor;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;


/**
 * TODO: document your custom view class.
 */

public class FoodListItemView extends FrameLayout {
    private Food viewModel;
    private FetchImageInteractor interactor;

    public FoodListItemView(Context context) {
        super(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        init();

    }


    private void init() {
        ViewGroup itemView =
                (ViewGroup) LayoutInflater.from(getContext())
                        .inflate(R.layout.food_list_item_view, null, false);
        addView(itemView);
    }

    public void fillDetails(Food viewModel) {
        this.viewModel = viewModel;
        TextView name = (TextView) findViewById(R.id.tv_food_name);
        TextView type = (TextView) findViewById(R.id.tv_food_type);

//
        name.setText(viewModel.getName());
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

    public Food getViewModel() {
        return viewModel;
    }
}
