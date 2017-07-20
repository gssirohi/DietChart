package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.domain.exception.AppRepositoryException;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;

/**
 * Created by gssirohi on 15/7/16.
 */
public class GetMealPlanInteractor extends BaseInteractor implements GetMealPlanUseCase {

    private IAppRepository appRepository;
    private Callback callback;

    private Long planId;

    public GetMealPlanInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            final MealPlan plan = appRepository.getMealPlan(this, planId);
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMealPlanFetched(plan);
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
    public void execute(Callback callback, Long id, boolean showLoader) {
        this.callback = callback;
        this.planId = id;
        setCancelled(false);
        if (showLoader) showDialog("Fetching Meal Plan..");
        getInteractorExecutor().performAction(this);
    }
}
