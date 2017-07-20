package com.techticz.app.domain.interactor;

import android.graphics.Bitmap;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.domain.model.pojo.MealPlan;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface UploadImageUseCase extends UseCase {
    void execute(final Callback callback, Bitmap bitmap, boolean showLoader);

    interface Callback {
        void onError(AppErrors error);
        void onImageUploaded(MealPlan planWithId);
    }
}
