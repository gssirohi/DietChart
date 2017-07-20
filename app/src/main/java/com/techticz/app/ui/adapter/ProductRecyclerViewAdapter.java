package com.techticz.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.techticz.app.R;
import com.techticz.app.ui.customview.ProductView;
import com.techticz.app.ui.viewmodel.contract.IProductViewModel;

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final ProductView.ProductViewContract contract;

    private int visibleThreshold = 2;

    private List<IProductViewModel> mValues;

    public ProductRecyclerViewAdapter(List<IProductViewModel> items, ProductView.ProductViewContract contract) {
        mValues = items;
        this.contract = contract;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            ProductView view = new ProductView(parent);
            view.setContract(contract);
            return new DataViewHolder(view);
        } else if (viewType == VIEW_PROG) {
            View view = View.inflate(parent.getContext(), R.layout.characters_list_item_loading_layout, null);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
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
//                    if (null != mListener) {
//                        mListener.onProductItemInteraction((Meal) ((DataViewHolder)holder).getView().getViewModel().getModel());
//                    }
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
        return !mValues.get(position).getName().equals("dummy") ? VIEW_ITEM : VIEW_PROG;
    }

    public List<IProductViewModel> getValues() {
        return mValues;
    }

    public void setValues(List<IProductViewModel> mValues) {
        this.mValues = mValues;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public final ProductView mView;

        public DataViewHolder(ProductView view) {
            super(view);
            mView = view;
        }

        public ProductView getView() {
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
