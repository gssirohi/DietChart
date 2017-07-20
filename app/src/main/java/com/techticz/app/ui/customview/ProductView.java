package com.techticz.app.ui.customview;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.domain.interactor.FetchImageInteractor;
import com.techticz.app.ui.presenter.IProductViewPresenter;
import com.techticz.app.ui.presenter.ProductViewPresenter;
import com.techticz.app.ui.viewcontract.ProductViewPresenterContract;
import com.techticz.app.ui.viewmodel.contract.IProductViewModel;


/**
 * TODO: document your custom view class.
 */

public class ProductView extends FrameLayout implements ProductViewPresenterContract {
    private IProductViewModel viewModel;
    private FetchImageInteractor interactor;
    private ImageView imageView;
    private TextView nameView;
    private IProductViewPresenter iPresenter;
    private ProductViewContract contract;

    public ProductView(ViewGroup parent, IProductViewModel viewModel) {
        super(parent.getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        this.viewModel = viewModel;
        init(parent);
        fillDetails(viewModel);

    }

    public ProductView(ViewGroup parent) {
        super(parent.getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        setLayoutParams(params);
        init(parent);
    }

    private void init(ViewGroup parent) {
        ViewGroup productView =
                (ViewGroup) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.product_list_item_layout, parent, false);
        addView(productView);
        imageView = (ImageView) productView.findViewById(R.id.iv_product_image);
        nameView = (TextView) productView.findViewById(R.id.tv_product_name);
        productView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductClicked();
            }
        });
    }

    private void onProductClicked() {

        if (contract != null) {
            contract.onProductViewClicked(viewModel);
        }
    }

    public void fillDetails(IProductViewModel viewModel) {
        this.viewModel = viewModel;
        nameView.setText(viewModel.getName());
        if (iPresenter == null) {
            iPresenter = new ProductViewPresenter(getContext());
            iPresenter.setView(this);
        } else {
            iPresenter.cancelCurrentRequest();
        }
        iPresenter.onRequestProductImage(viewModel.getResourceUri());
    }

    @Override
    public void onProductImageFetched(Bitmap image) {
        imageView.setImageBitmap(image);
    }

    @Override
    public void onError(String s) {

    }

    public void setContract(ProductViewContract contract) {
        this.contract = contract;
    }

    public interface ProductViewContract {
        public void onProductViewClicked(IProductViewModel viewModel);
    }
}
