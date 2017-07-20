package com.techticz.app.ui.customview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.domain.interactor.FetchImageInteractor;
import com.techticz.app.ui.adapter.EndlessRecyclerOnScrollListener;
import com.techticz.app.ui.adapter.ProductRecyclerViewAdapter;
import com.techticz.app.ui.viewmodel.contract.IProductCardViewModel;
import com.techticz.app.ui.viewmodel.contract.IProductViewModel;


/**
 * TODO: document your custom view class.
 */

public class ProductCardView extends FrameLayout implements ProductView.ProductViewContract {
    private final IProductCardViewModel viewModel;
    private FetchImageInteractor interactor;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private ProductRecyclerViewAdapter mRecyclerAdapter;
    private TextView prductCardName;
    private TextView prductAvailabe;
    private TextView prductShowing;
    private ProductCardViewContract contract;

    public ProductCardView(Context context, IProductCardViewModel viewModel) {
        super(context);
        this.viewModel = viewModel;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        init(context);

    }


    private void init(Context context) {
        ViewGroup productView =
                (ViewGroup) LayoutInflater.from(context)
                        .inflate(R.layout.product_layout, null, false);
        recyclerView = (RecyclerView) productView.findViewById(R.id.product_list);
        prductCardName = (TextView) productView.findViewById(R.id.tv_product_lable);
        prductAvailabe = (TextView) productView.findViewById(R.id.tv_available);
        prductShowing = (TextView) productView.findViewById(R.id.tv_showing);
        addView(productView);
        fillDetails();
    }

    public void fillDetails() {

        prductCardName.setText(viewModel.getProductCardLable());
        prductAvailabe.setText("Available: " + viewModel.getAvailabe());
        prductShowing.setText("Showing: " + viewModel.getShowing());
        Context context = getContext();
        this.mLayoutManager = new LinearLayoutManager(context);
        this.mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);

        mRecyclerAdapter = new ProductRecyclerViewAdapter(viewModel.getProductViewModelList(), this);
        recyclerView.setAdapter(mRecyclerAdapter);
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                //mRecyclerAdapter.getValues().add(character);
                mRecyclerAdapter.notifyDataSetChanged();
                loadMoreData();
            }
        });
    }

    private void loadMoreData() {

    }

    @Override
    public void onProductViewClicked(IProductViewModel viewModel) {

        if (contract != null) {
            contract.onCardViewItemClicked(viewModel);
        }
    }

    public void setContract(ProductCardViewContract contract) {
        this.contract = contract;
    }

    public interface ProductCardViewContract {
        public void onCardViewItemClicked(IProductViewModel viewModel);
    }
}
