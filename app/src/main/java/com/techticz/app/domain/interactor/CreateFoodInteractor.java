package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.domain.exception.AppRepositoryException;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;

/**
 * Created by gssirohi on 15/7/16.
 */
public class CreateFoodInteractor extends BaseInteractor implements CreateFoodUseCase {

    private IAppRepository appRepository;
    private Callback callback;
    private Meal meal;
    private Food food;

    public CreateFoodInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            Thread.sleep(300);
            final long foodId = appRepository.createFood(this, food);
            food.setUid(foodId);
            //now upload food image
            ImageUploadResponse blob = appRepository.uploadImage(this, food.getBitmap(), food.getName().trim());
            food.setBlobServingUrl(blob.getServingUrl());
            food.setBlobKey(blob.getBlobKey());
            AppLogger.i(this,"BLOB KEY:"+food.getBlobKey());
            appRepository.updateFood(this,food);
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFoodCreated(food);
                    }
                });

        } catch (final AppRepositoryException e) {
            AppLogger.e(this, "Error on create meal");
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
    public void execute(Callback callback, Food food, boolean showLoader) {
        this.callback = callback;
        this.food = food;
        if (showLoader) {
            showDialog("Creating Food .. ");
        }
        getInteractorExecutor().performAction(this);
    }
}
