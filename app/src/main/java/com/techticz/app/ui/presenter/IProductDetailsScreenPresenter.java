package com.techticz.app.ui.presenter;

import com.techticz.app.base.IBasePresenter;
import com.techticz.app.ui.viewcontract.ProductDeatailsScreenViewContract;

/**
 * Created by gssirohi on 5/7/16.
 */
public interface IProductDetailsScreenPresenter extends IBasePresenter<ProductDeatailsScreenViewContract> {

    void onFetchProductDetailsRequest(String uri, boolean showLoader);

}
