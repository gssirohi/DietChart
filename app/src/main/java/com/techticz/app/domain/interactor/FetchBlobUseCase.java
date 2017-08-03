package com.techticz.app.domain.interactor;

import android.graphics.Bitmap;

import com.techticz.app.constant.AppErrors;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;


/**
 * Created by gssirohi on 29/8/16.
 */
public interface FetchBlobUseCase extends UseCase {
    void execute(final Callback callback, boolean showLoader,String blobKey,String servingUrl);

    interface Callback {
        void onError(AppErrors error);
        void onBlobFetched(String blobKey, Bitmap bitmap);
    }
}
