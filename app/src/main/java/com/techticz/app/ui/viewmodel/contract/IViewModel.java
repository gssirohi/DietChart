package com.techticz.app.ui.viewmodel.contract;

import com.techticz.app.domain.model.Model;

/**
 * Created by gssirohi on 7/7/16.
 */
public interface IViewModel {

    public <T extends Model> T getModel();


}
