package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.base.AppCore;
import com.techticz.app.constant.Repositories;
import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.model.pojo.AddedFood;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;

import java.util.Iterator;
import java.util.List;

/**
 * Created by gssirohi on 15/7/16.
 */
public class FetchMealListInteractor extends BaseInteractor implements FetchMealListUseCase {

    private IAppRepository appRepository;
    private Callback callback;
    private int day;
    private String searchKey;
    private long[] mealIds;

    public FetchMealListInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            final List<Meal> meals = appRepository.getMealList(this,10, searchKey, mealIds);
            for(Meal m: meals){
                loadFoods(m);
            }
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMealListFetched(meals, searchKey);
                    }
                });

        } catch (final AppException e) {
            AppLogger.e(this, "Error on fetch meals");
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e.getError());
                    }
                });
        }
    }

    private void loadFoods(Meal meal) {
        if(meal == null) return;
        IAppRepository appRepository = AppCore.getInstance().getProvider().getAppRepository(Repositories.DATABASE);
        List<AddedFood> list = meal.getAddedFoods();
        if(list != null){
            Iterator<AddedFood> it = list.iterator();
            while (it.hasNext()){
                AddedFood af = it.next();
                List<Food> fss = appRepository.getFoodList(null, "", new Long[]{af.getFoodId()});
                af.setFood(fss.get(0));
            }
        }
    }

    @Override
    public void cancelCurrentRequest() {
        setCancelled(true);
    }


    @Override
    public void execute(Callback callback, boolean showLoader, String searchKey, long[] mealIds) {
        this.callback = callback;
        this.searchKey = searchKey;
        this.mealIds = mealIds;
        if (showLoader) showDialog("Searching meal(s) .. ");
        getInteractorExecutor().performAction(this);
    }
}
