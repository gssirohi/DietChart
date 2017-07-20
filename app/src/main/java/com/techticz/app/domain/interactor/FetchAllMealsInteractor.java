package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.domain.exception.AppRepositoryException;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;

import java.util.List;

/**
 * Created by gssirohi on 15/7/16.
 */
public class FetchAllMealsInteractor extends BaseInteractor implements FetchAllMealsUseCase {

    private IAppRepository appRepository;
    private Callback callback;
    private int day;
    private String searchKey;
    private int[] mealIds;

    public FetchAllMealsInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            Thread.sleep(300);

            final List<Meal> meals = appRepository.getMealList(this, 100, searchKey, mealIds);
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onAllMealsFetched(meals, searchKey);
                    }
                });

        } catch (final AppRepositoryException e) {
            AppLogger.e(this, "Error on fetch routines for day " + day);
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
    public void execute(Callback callback, boolean showLoader, String searchKey, int[] mealIds) {
        this.callback = callback;
        this.searchKey = searchKey;
        this.mealIds = mealIds;
        if (showLoader) showDialog("Loading all meals .. ");
        getInteractorExecutor().performAction(this);
    }
}
