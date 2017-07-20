package com.techticz.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.techticz.app.R;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.ui.customview.MealListItemView;
import com.techticz.app.ui.customview.ProductView;
import com.techticz.app.ui.viewmodel.contract.IMealViewModel;
import com.techticz.app.ui.viewmodel.contract.IProductViewModel;

import java.util.List;

public class BrowseMealRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final MealViewContract contract;

    private int visibleThreshold = 2;

    private List<Meal> mValues;

    public BrowseMealRecyclerViewAdapter(List<Meal> items, MealViewContract contract) {
        mValues = items;
        this.contract = contract;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            MealListItemView view = new MealListItemView(parent.getContext());

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
                        contract.onMealItemClicked(mValues.get(position));
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

    public List<Meal> getValues() {
        return mValues;
    }

    public void setValues(List<Meal> mValues) {
        this.mValues = mValues;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public final MealListItemView mView;

        public DataViewHolder(MealListItemView view) {
            super(view);
            mView = view;
        }

        public MealListItemView getView() {
            return mView;
        }
    }

    public interface MealViewContract {
        void onMealItemClicked(Meal viewModel);
    }

}
