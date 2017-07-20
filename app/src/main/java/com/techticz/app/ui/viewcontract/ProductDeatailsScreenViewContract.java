package com.techticz.app.ui.viewcontract;

import com.techticz.app.ui.viewmodel.ProductDetailsViewModel;

/**
 * Created by gssirohi on 13/9/16.
 */
public interface ProductDeatailsScreenViewContract extends ViewContract {
    void onProductDetailsFetched(ProductDetailsViewModel viewModel);
}
