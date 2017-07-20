package com.techticz.app.ui.viewmodel.contract;

/**
 * Created by gssirohi on 7/7/16.
 */
public interface IMealRoutineViewModel extends IViewModel {

    public String getName();

    public String getDescription();

    public IMealViewModel getMealViewModel();

    int getId();
}
