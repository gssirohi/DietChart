package com.techticz.app.ui.viewmodel.contract;

import com.techticz.app.domain.model.pojo.Nutrition;

import java.util.List;

/**
 * Created by gssirohi on 7/7/16.
 */
public interface IMealViewModel extends IViewModel {

    public String getName();

    public String getDescription();

    String getType();

    List<Nutrition> getNutritions();
}
