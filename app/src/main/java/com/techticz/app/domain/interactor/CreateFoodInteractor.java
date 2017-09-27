package com.techticz.app.domain.interactor;

import android.content.Context;
import android.text.TextUtils;

import com.techticz.app.base.AppCore;
import com.techticz.app.constant.Repositories;
import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.model.pojo.Food;
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
public class CreateFoodInteractor extends BaseInteractor implements CreateFoodUseCase {

    private IAppRepository appRepository;
    private Callback callback;
    private Food food;

    public CreateFoodInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {

            IAppRepository db = AppCore.getInstance().getProvider().getAppRepository(Repositories.DATABASE);
            if(food.getUid() != null && food.getUid() != 0 ) {
                if(TextUtils.isEmpty(food.getBlobServingUrl())){
                    ImageUploadResponse blob = appRepository.uploadImage(this, food.getBitmap(), AppUtils.getBitmapName(food));
                    food.setBlobServingUrl(blob.getServingUrl());
                    food.setBlobKey(blob.getBlobKey());
                    AppLogger.i(this,"BLOB KEY:"+food.getBlobKey());
                }
                db.updateFood(this,food);
                appRepository.updateFood(this,food);
            } else {
                long foodId = appRepository.createFood(this, food);
                food.setUid(foodId);
                db.createFood(this,food);
                ImageUploadResponse blob = appRepository.uploadImage(this, food.getBitmap(), AppUtils.getBitmapName(food));
                food.setBlobServingUrl(blob.getServingUrl());
                food.setBlobKey(blob.getBlobKey());
                AppLogger.i(this,"BLOB KEY:"+food.getBlobKey());
                db.updateFood(this,food);
                appRepository.updateFood(this,food);
            }


            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFoodCreated(food);
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
    public void execute(Callback callback, Food food, boolean showLoader) {
        this.callback = callback;
        this.food = food;
        if (showLoader) {
            showDialog("Creating Food .. ");
        }
        getInteractorExecutor().performAction(this);
    }
}
