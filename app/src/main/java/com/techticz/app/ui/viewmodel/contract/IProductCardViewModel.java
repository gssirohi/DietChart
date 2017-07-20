package com.techticz.app.ui.viewmodel.contract;

import java.util.List;

/**
 * Created by gssirohi on 7/7/16.
 */
public interface IProductCardViewModel extends IViewModel {

    String getProductCardLable();

    int getAvailabe();

    int getShowing();

    List<IProductViewModel> getProductViewModelList();
}
