package com.techticz.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.ui.customview.FoodListItemView;
import com.techticz.app.ui.customview.MealListItemView;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;

import java.util.List;

public class BrowseFoodRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final FoodViewContract contract;

    private int visibleThreshold = 2;

    private List<Food> mValues;

    public BrowseFoodRecyclerViewAdapter(List<Food> items, FoodViewContract contract) {
        mValues = items;
        this.contract = contract;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            FoodListItemView view = new FoodListItemView(parent.getContext());

            return new DataViewHolder(view);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DataViewHolder) {
            // holder.mItem = mValues.get(position);
            ((DataViewHolder) holder).mView.fillDetails(mValues.get(position));
            ((DataViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != contract) {
                        contract.onFoodItemClicked(mValues.get(position));
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_ITEM;
    }

    public List<Food> getValues() {
        return mValues;
    }

    public void setValues(List<Food> mValues) {
        this.mValues = mValues;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public final FoodListItemView mView;

        public DataViewHolder(FoodListItemView view) {
            super(view);
            mView = view;
        }

        public FoodListItemView getView() {
            return mView;
        }
    }

    public interface FoodViewContract {
        void onFoodItemClicked(Food food);
    }

}
