package com.techticz.app.ui.viewcontract;

import android.graphics.Bitmap;

/**
 * Created by gssirohi on 5/7/16.
 */
public interface ProductViewPresenterContract extends ViewContract {
    void onProductImageFetched(Bitmap image);
}
