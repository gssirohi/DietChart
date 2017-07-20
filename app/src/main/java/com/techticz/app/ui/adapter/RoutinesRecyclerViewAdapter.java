package com.techticz.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.techticz.app.R;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.ui.customview.MealRoutineView;
import com.techticz.app.ui.fragment.DayMealsListFragment;

import com.techticz.app.ui.viewmodel.contract.IMealRoutineViewModel;

import java.util.List;

public class RoutinesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final int tab;

    private int visibleThreshold = 2;

    private List<MealRoutine> mValues;
    private final DayMealsListFragment.OnListFragmentInteractionListener mListener;

    public RoutinesRecyclerViewAdapter(int tab, List<MealRoutine> items, DayMealsListFragment.OnListFragmentInteractionListener listener) {
        this.tab = tab;
        mValues = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            MealRoutineView view = new MealRoutineView(tab, parent);
            return new DataViewHolder(view);
        } else if (viewType == VIEW_PROG) {
            View view = View.inflate(parent.getContext(), R.layout.characters_list_item_loading_layout, null);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
            return new ProgressViewHolder(view);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataViewHolder) {
            // holder.mItem = mValues.get(position);
            ((DataViewHolder) holder).mView.fillDetails(mValues.get(position));
            ((DataViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        MealRoutine character = ((DataViewHolder) holder).getView().getViewModel();
                        mListener.onListFragmentItemInteraction(character);
                    }
                }
            });
        } else {
            ProgressBar pb = (ProgressBar) ((ProgressViewHolder) holder).progressView.findViewById(R.id.progress);
            pb.setIndeterminate(true);
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

    public List<MealRoutine> getValues() {
        return mValues;
    }

    public void setValues(List<MealRoutine> mValues) {
        this.mValues = mValues;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public final MealRoutineView mView;

        public DataViewHolder(MealRoutineView view) {
            super(view);
            mView = view;
        }

        public MealRoutineView getView() {
            return mView;
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public View progressView;

        public ProgressViewHolder(View v) {
            super(v);
            progressView = v;
        }
    }
}
