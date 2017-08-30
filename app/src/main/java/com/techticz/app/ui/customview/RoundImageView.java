package com.techticz.app.ui.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.FetchBlobUseCase;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 26/8/17.
 */

public class RoundImageView extends CircleImageView implements FetchBlobUseCase.Callback {
    private String url;
    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setUrl(String url){
        this.url = url;
        FetchBlobUseCase usecase = (FetchBlobUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(getContext(), UseCases.FETCH_BLOB);
        usecase.execute(this,false,"",url);
        setImageResource(R.drawable.app_icon);

    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onBlobFetched(String blobKey, Bitmap bitmap) {
        this.setImageBitmap(bitmap);
    }
}
