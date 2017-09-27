package com.techticz.app.domain.interactor;

import android.content.Context;
import android.graphics.Bitmap;

import com.techticz.app.base.AppCore;
import com.techticz.app.constant.Repositories;
import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.domain.repository.cache.CacheRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;

import java.net.MalformedURLException;

/**
 * Created by gssirohi on 15/7/16.
 */
public class FetchBlobInteractor extends BaseInteractor implements FetchBlobUseCase {

    private IAppRepository appRepository;
    private Callback callback;
        private String blobKey;
    private String servingUrl;

    public FetchBlobInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            Thread.sleep(300);
             Bitmap response = null;
            try {
                response = ((CacheRepository) AppCore.getInstance().getProvider()
                        .getAppRepository(Repositories.CACHE)).fetchBlob(this,blobKey,servingUrl);
                if(response == null) {
                    response = appRepository.fetchBlob(this, blobKey, servingUrl);
                    ((CacheRepository) AppCore.getInstance().getProvider()
                            .getAppRepository(Repositories.CACHE))
                            .addBitmapToMemoryCache(servingUrl, response);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            dismissDialog();
            if (!isCancelled()) {

                final Bitmap finalResponse = response;


                getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onBlobFetched(blobKey, finalResponse);
                        }
                    });

            }

        } catch (final AppException e) {
            AppLogger.e(this, "Error on fetching blob "+blobKey);
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e.getError());
                    }
                });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelCurrentRequest() {
        setCancelled(true);
    }

    @Override
    public void execute(Callback callback, boolean showLoader, String blobKey,String servingUrl) {
        this.callback = callback;
        this.servingUrl = servingUrl;
        this.blobKey = blobKey;
        if (showLoader) {
            showDialog("Fetching Blob .. ");
        }
        getInteractorExecutor().performAction(this);
    }
}
