package com.techticz.app.ui.presenter;

import com.techticz.app.base.IBasePresenter;
import com.techticz.app.ui.viewcontract.ProductViewPresenterContract;

/**
 * Created by gssirohi on 5/7/16.
 */
public interface IProductViewPresenter extends IBasePresenter<ProductViewPresenterContract> {

    void onRequestProductImage(String productUri);

}
