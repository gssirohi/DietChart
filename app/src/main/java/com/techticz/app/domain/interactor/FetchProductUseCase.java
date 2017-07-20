package com.techticz.app.domain.interactor;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.Products;
import com.techticz.app.domain.model.ProductModel;

/**
 * Created by gssirohi on 29/8/16.
 */
public interface FetchProductUseCase extends UseCase {
    void execute(final Callback callback, String id, Products type, boolean showLoader);

    interface Callback {
        void onError(AppErrors error);

        <T extends ProductModel> void onProduct(T product);
    }
}
