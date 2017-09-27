package com.techticz.app.domain.interactor;

import android.content.Context;
import android.text.TextUtils;

import com.techticz.app.base.AppCore;
import com.techticz.app.constant.Repositories;
import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;
import com.techticz.app.utility.AppUtils;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;

/**
 * Created by gssirohi on 15/7/16.
 */
public class CreateMealInteractor extends BaseInteractor implements CreateMealUseCase {

    private IAppRepository appRepository;
    private Callback callback;
    private Meal meal;

    public CreateMealInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            final long mealId;

            IAppRepository db = AppCore.getInstance().getProvider().getAppRepository(Repositories.DATABASE);
            if(meal.getUid() != null && meal.getUid() != 0 ) {
                if(TextUtils.isEmpty(meal.getBlobServingUrl())){
                    ImageUploadResponse blob = appRepository.uploadImage(this, meal.getBitmap(), AppUtils.getBitmapName(meal));
                    meal.setBlobServingUrl(blob.getServingUrl());
                    meal.setBlobKey(blob.getBlobKey());
                    AppLogger.i(this,"BLOB KEY:"+meal.getBlobKey());
                }
                db.updateMeal(this,meal);
                mealId = appRepository.updateMeal(this,meal);
            } else {
                mealId = appRepository.createMeal(this, meal);
                meal.setUid(mealId);
                db.createMeal(this,meal);
                ImageUploadResponse blob = appRepository.uploadImage(this, meal.getBitmap(), AppUtils.getBitmapName(meal));
                meal.setBlobServingUrl(blob.getServingUrl());
                meal.setBlobKey(blob.getBlobKey());
                AppLogger.i(this,"BLOB KEY:"+meal.getBlobKey());
                db.updateMeal(this,meal);
                appRepository.updateMeal(this,meal);
            }

            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMealCreated(meal);
                    }
                });

        } catch (final AppException e) {
            AppLogger.e(this, "Error on create meal");
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e.getError());
                    }
                });
        }
    }

    @Override
    public void cancelCurrentRequest() {
        setCancelled(true);
    }

    @Override
    public void execute(Callback callback, Meal meal, boolean showLoader) {
        this.callback = callback;
        this.meal = meal;
        if (showLoader) {
            showDialog("Creating Meal .. ");
        }
        getInteractorExecutor().performAction(this);
    }
}
