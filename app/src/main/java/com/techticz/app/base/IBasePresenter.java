package com.techticz.app.base;


import com.techticz.app.ui.viewcontract.ViewContract;

public interface IBasePresenter<T extends ViewContract> {

    void initialize();

    void onViewCreate();

    void onViewResume();

    void onViewDestroy();

    void setView(T view);

    void cancelCurrentRequest();
}

