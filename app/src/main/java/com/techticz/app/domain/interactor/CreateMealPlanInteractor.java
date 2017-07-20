package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.constant.Products;
import com.techticz.app.domain.exception.AppRepositoryException;
import com.techticz.app.domain.model.ProductModel;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;

/**
 * Created by gssirohi on 15/7/16.
 */
public class CreateMealPlanInteractor extends BaseInteractor implements CreateMealPlanUseCase {

    private IAppRepository appRepository;
    private Callback callback;

    private MealPlan plan;

    public CreateMealPlanInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            if (plan.getUid() == null || plan.getUid() == 0) {
                long id = appRepository.createMealPlan(this, plan);
                plan.setUid(id);
            } else {
                appRepository.updateMealPlan(this, plan);
            }
            plan = appRepository.getMealPlan(this, plan.getUid());
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMealPlanCreated(plan);
                    }
                });

        } catch (final AppRepositoryException e) {
            AppLogger.e(this, "Error on fetch all characters");
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
    public void execute(Callback callback, MealPlan plan, boolean showLoader) {
        this.callback = callback;
        this.plan = plan;
        setCancelled(false);
        if (showLoader) showDialog("creating Meal Plan..");
        getInteractorExecutor().performAction(this);
    }
}
