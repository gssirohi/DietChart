package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.base.AppCore;
import com.techticz.app.constant.Repositories;
import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.model.pojo.AddedFood;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.domain.repository.api.APIRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;

import java.util.Iterator;
import java.util.List;

/**
 * Created by gssirohi on 15/7/16.
 */
public class FetchMealDetailsInteractor extends BaseInteractor implements FetchMealDetailsUseCase {

    private IAppRepository appRepository;
    private Callback callback;
    private long mealId;

    public FetchMealDetailsInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            Thread.sleep(300);

            final Meal meal = appRepository.getMealDetails(this,mealId);
            loadFoods(meal);
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMealDetailsFetched(meal);
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadFoods(Meal meal) {
        List<AddedFood> list = meal.getAddedFoods();
        if(list != null){
            Iterator<AddedFood> it = list.iterator();
            while (it.hasNext()){
                AddedFood af = it.next();
                List<Food> fss = appRepository.getFoodList(this, "", new Long[]{af.getFoodId()});
                af.setFood(fss.get(0));
            }
        }
    }

    @Override
    public void cancelCurrentRequest() {
        setCancelled(true);
    }


    @Override
    public void execute(Callback callback, boolean showLoader, long mealId) {
        this.callback = callback;
        this.mealId = mealId;
        if (showLoader) showDialog("Fetching meal details.. ");
        getInteractorExecutor().performAction(this);
    }
}
