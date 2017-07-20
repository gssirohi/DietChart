package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;


/**
 * Created by gssirohi on 29/8/16.
 */
public interface CheckSystemHealthUseCase extends UseCase {
    void execute(final Callback callback, boolean showLoader);

    interface Callback {
        void onError(AppErrors error);


        void onSystemHealth(SystemHealth health);
    }
}
