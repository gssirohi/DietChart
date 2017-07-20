package com.techticz.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.ui.customview.MealPlanListItemView;

import java.util.List;

public class BrowsePlanRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final MealPlanViewContract contract;

    private int visibleThreshold = 2;

    private List<MealPlan> mValues;

    public BrowsePlanRecyclerViewAdapter(List<MealPlan> items, MealPlanViewContract contract) {
        mValues = items;
        this.contract = contract;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            MealPlanListItemView view = new MealPlanListItemView(parent.getContext());

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
                        contract.onMealPlanItemClicked(mValues.get(position));
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

    public List<MealPlan> getValues() {
        return mValues;
    }

    public void setValues(List<MealPlan> mValues) {
        this.mValues = mValues;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public final MealPlanListItemView mView;

        public DataViewHolder(MealPlanListItemView view) {
            super(view);
            mView = view;
        }

        public MealPlanListItemView getView() {
            return mView;
        }
    }

    public interface MealPlanViewContract {
        void onMealPlanItemClicked(MealPlan MealPlan);
    }

}
