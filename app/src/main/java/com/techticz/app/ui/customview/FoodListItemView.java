package com.techticz.app.ui.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.FoodType;
import com.techticz.app.constant.Servings;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.FetchBlobUseCase;
import com.techticz.app.domain.interactor.FetchImageInteractor;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;


/**
 * TODO: document your custom view class.
 */

public class FoodListItemView extends FrameLayout {
    private Food viewModel;
    private FetchImageInteractor interactor;
    private RoundImageView civ;
    private CheckBox cb_food;
    private boolean isSelected;
    private SelectionListner foodSelectListner;
    private EditText et_serving;
    private TextView tv_serving_label;

    public FoodListItemView(Context context) {
        super(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        if(context instanceof SelectionListner){
            setFoodSelectListner((SelectionListner)context);
        }
        init();
    }


    public void displayCheckBox(boolean display){
        if(display)cb_food.setVisibility(VISIBLE);
        else
        cb_food.setVisibility(GONE);

        if(!display){
            isSelected = false;
        }
        cb_food.setChecked(isSelected);
    }
    public void selectFood(boolean select){
        isSelected = select;
        cb_food.setChecked(select);
    }
    private void init() {
        ViewGroup itemView =
                (ViewGroup) LayoutInflater.from(getContext())
                        .inflate(R.layout.food_list_item_view, null, false);
        addView(itemView);
        civ = (RoundImageView)findViewById(R.id.civ_food);
        cb_food = (CheckBox)findViewById(R.id.cb_food);
        et_serving = (EditText)findViewById(R.id.et_serving);
        tv_serving_label = (TextView)findViewById(R.id.tv_serving_label);
        et_serving.setEnabled(false);
    }

    public void setFoodSelectListner(SelectionListner foodSelectListner) {
        this.foodSelectListner = foodSelectListner;
    }

    public void fillDetails(final Food viewModel){
        fillDetails(viewModel,-1);
    }
    public void fillDetails(final Food viewModel,int serving) {
        this.viewModel = viewModel;
        TextView name = (TextView) findViewById(R.id.tv_food_name);
        TextView type = (TextView) findViewById(R.id.tv_food_type);
        cb_food.setVisibility(View.GONE);
        final Food food = viewModel;
        cb_food.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isSelected = b;
                if(foodSelectListner != null) {
                    foodSelectListner.onFoodSelectionChanged(food.getUid(),b);
                }
            }
        });
//
        name.setText(viewModel.getName());
        type.setText(FoodType.getById(viewModel.getType()).lable);

        tv_serving_label.setText(Servings.getById(viewModel.getServingId()).lable);
        if(serving != -1) {
            et_serving.setText(""+serving);
        }/* else {
            tv_serving_label.setText("Calories");
            et_serving.setText(""+viewModel.getCalory());
        }*/
        setFoodImage(civ);

    }

    private void setFoodImage(RoundImageView civ) {
        civ.setUrl(viewModel.getBlobServingUrl());
    }

    public Food getViewModel() {
        return viewModel;
    }

    public boolean isFoodSelected() {
        return isSelected;
    }

    public int getServing() {
        EditText et = (EditText) findViewById(R.id.et_serving);
        return Integer.parseInt(et.getText().toString());
    }

    public void servingEdit(boolean b) {
        et_serving.setEnabled(b);
    }

    public interface SelectionListner{
        public void onFoodSelectionChanged(long id,boolean b);
    }
}
