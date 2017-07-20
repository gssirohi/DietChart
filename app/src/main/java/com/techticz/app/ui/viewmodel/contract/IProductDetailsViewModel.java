package com.techticz.app.ui.viewmodel.contract;

import com.techticz.app.ui.viewmodel.ProductCardViewModel;

import java.util.List;

/**
 * Created by gssirohi on 7/7/16.
 */
public interface IProductDetailsViewModel extends IViewModel {

    public String getId();

    public String getName();


    String getImageUrl();

    List<ProductCardViewModel> getProductCardViewModelList();

    String getDescription();
}
