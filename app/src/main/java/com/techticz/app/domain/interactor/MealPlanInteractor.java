package com.techticz.app.domain.interactor;

import android.content.Context;
import android.text.TextUtils;

import com.techticz.app.base.AppCore;
import com.techticz.app.constant.Repositories;
import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;
import com.techticz.app.utility.AppUtils;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;

import java.util.List;

/**
 * Created by gssirohi on 15/7/16.
 */
public class MealPlanInteractor extends BaseInteractor implements MealPlanUseCase {

    public static final int ACTION_CREATE = 1;
    public static final int ACTION_UPDATE = 2;
    public static final int ACTION_AUTO_LOAD = 3;

    private IAppRepository appRepository;
    private Callback callback;

    private MealPlan plan;
    private long planId;
    private boolean autoLoad;
    private List<Integer> prefRoutines;
    private int action = 0;

    public MealPlanInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            IAppRepository db = AppCore.getInstance().getProvider().getAppRepository(Repositories.DATABASE);
            if((action == ACTION_UPDATE || action == ACTION_AUTO_LOAD) && plan.getUid() != null && plan.getUid() != 0 ) {
                if(TextUtils.isEmpty(plan.getBlobServingUrl()) && plan.getBitmap() != null){
                    ImageUploadResponse blob = appRepository.uploadImage(this, plan.getBitmap(), AppUtils.getBitmapName(plan));
                    plan.setBlobServingUrl(blob.getServingUrl());
                    AppLogger.i(this,"BLOB URL:"+plan.getBlobServingUrl());
                }
                MealPlan loadedPlan = appRepository.updateMealPlan(this,plan,autoLoad,prefRoutines);
                loadedPlan.setBitmap(plan.getBitmap());
                plan = loadedPlan;
                db.updateMealPlan(this,plan,autoLoad,prefRoutines);
            } else if(action == ACTION_CREATE){
                planId = appRepository.createMealPlan(this, plan,autoLoad,prefRoutines);
                if(autoLoad){
                    MealPlan loadedPlan = appRepository.getMealPlan(this, planId);
                    loadedPlan.setBitmap(plan.getBitmap());
                    plan = loadedPlan;
                } else {
                    plan.setUid(planId);
                }
                db.createMealPlan(this,plan,autoLoad,prefRoutines);
                ImageUploadResponse blob = appRepository.uploadImage(this, plan.getBitmap(), AppUtils.getBitmapName(plan));
                plan.setBlobServingUrl(blob.getServingUrl());
                AppLogger.i(this,"BLOB URL:"+plan.getBlobServingUrl());
                db.updateMealPlan(this,plan, autoLoad, prefRoutines);
                appRepository.updateMealPlan(this,plan, autoLoad, prefRoutines);
            }
            //--------------------------------------------

            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMealPlanCreated(plan);
                    }
                });

        } catch (final AppException e) {
            AppLogger.e(this, "Error on create meal plan");
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
    public void createMealPlan(Callback callback, MealPlan plan, boolean autoLoad, List<Integer> pRoutines, boolean showLoader) {
        this.action = ACTION_CREATE;
        this.callback = callback;
        this.plan = plan;
        this.autoLoad = autoLoad;
        this.prefRoutines = pRoutines;
        setCancelled(false);
        if (showLoader) showDialog("Creating Meal Plan..");
        getInteractorExecutor().performAction(this);
    }

    @Override
    public void updateMealPlan(Callback callback, MealPlan plan, boolean showLoader) {
        this.action = ACTION_UPDATE;
        this.callback = callback;
        this.plan = plan;
        setCancelled(false);
        if (showLoader) showDialog("Updating Meal Plan..");
        getInteractorExecutor().performAction(this);
    }

    @Override
    public void autoLoadMealPlan(Callback callback, MealPlan plan, boolean autoLoad, List<Integer> pRoutines, boolean showLoader) {
        this.action = ACTION_AUTO_LOAD;
        this.callback = callback;
        this.plan = plan;
        this.autoLoad = autoLoad;
        this.prefRoutines = pRoutines;
        setCancelled(false);
        if (showLoader) showDialog("Adding Meals to your plan..");
        getInteractorExecutor().performAction(this);
    }
}
