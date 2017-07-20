package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.domain.exception.AppRepositoryException;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;

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
            Thread.sleep(300);
            final long mealId = appRepository.createMeal(this, meal);
            meal.setUid(mealId);
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMealCreated(meal);
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
    public void execute(Callback callback, Meal meal, boolean showLoader) {
        this.callback = callback;
        this.meal = meal;
        if (showLoader) {
            showDialog("Creating Meal .. ");
        }
        getInteractorExecutor().performAction(this);
    }
}