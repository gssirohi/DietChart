package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.domain.exception.AppRepositoryException;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;

import java.util.List;

/**
 * Created by gssirohi on 15/7/16.
 */
public class FetchMealPlanListInteractor extends BaseInteractor implements FetchMealPlanListUseCase {

    private IAppRepository appRepository;
    private Callback callback;
    private int day;
    private String searchKey;
    private boolean isMyPlan;

    public FetchMealPlanListInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            Thread.sleep(300);

            final List<MealPlan> mealPlans = appRepository.getMealPlans(this, searchKey, isMyPlan);
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMealPlanListFetched(mealPlans, searchKey, isMyPlan);
                    }
                });

        } catch (final AppRepositoryException e) {
            AppLogger.e(this, "Error on fetch plans");
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
    public void execute(Callback callback, boolean showLoader, String searchKey, boolean isMyPlan) {
        this.callback = callback;
        this.searchKey = searchKey;
        this.isMyPlan = isMyPlan;
        if (showLoader) showDialog("Searching meal plans .. ");
        getInteractorExecutor().performAction(this);
    }
}
