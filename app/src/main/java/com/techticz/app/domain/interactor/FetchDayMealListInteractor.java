package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.base.AppCore;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.Repositories;
import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.model.pojo.AddedFood;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealRoutine;
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
public class FetchDayMealListInteractor extends BaseInteractor implements FetchDayMealListUseCase {

    private IAppRepository appRepository;
    private Callback callback;
    private int day;
    private DayMeals dayMeals;

    public FetchDayMealListInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            final List<Meal> meals = appRepository.getDayMealList(this, day, dayMeals);
            IAppRepository repo = AppCore.getInstance().getProvider().getAppRepository(Repositories.DATABASE);
            final List<MealRoutine> routines = repo.getMealRoutinesByDay(this, day);

            int i = 0;
            for (MealRoutine routine : routines) {
                Meal meal = meals.get(i++);
                loadFoods(meal);
                routine.setMeal(meal);
            }

            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onRoutineMealList(day, routines);
                    }
                });

        } catch (final AppException e) {
            AppLogger.e(this, "Error on fetch routines for day " + day);
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e.getError());
                    }
                });
        }catch(Exception e){
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    callback.onError(AppErrors.PRODUCT_LIST_NULL);
                }
            });
        }
    }
    private void loadFoods(Meal meal) {
        if(meal == null) return;
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
    public void execute(Callback callback, int day, DayMeals dayMeals, boolean showLoader) {
        this.callback = callback;
        this.day = day;
        this.dayMeals = dayMeals;
        if (showLoader) showDialog("Loading Day meals .. ");
        getInteractorExecutor().performAction(this);
    }
}
