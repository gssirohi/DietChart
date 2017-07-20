package com.techticz.app.domain.interactor;

import android.graphics.Bitmap;

import com.techticz.app.constant.AppErrors;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface LoadImageUseCase extends UseCase {
    void execute(final Callback callback, String imageUrl, boolean showLoader);

    interface Callback {
        void onError(AppErrors error);

        void onImage(Bitmap image);
    }
}
