package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.model.pojo.AddedFood;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.NutitionInfo;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.domain.repository.database.Converters;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;

import java.util.Iterator;
import java.util.List;

/**
 * Created by gssirohi on 15/7/16.
 */
public class ExtractNutritionInfoInteractor extends BaseInteractor implements IExtractNutritientUseCase {

    private IAppRepository appRepository;
    private Callback callback;

    private Meal meal;
    private DayMeals dayMeals;
    private MealPlan mealPlan;

    public ExtractNutritionInfoInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            final NutitionInfo mealNF = new NutitionInfo();
            final NutitionInfo dayMealNF = new NutitionInfo();
            final NutitionInfo planNF = new NutitionInfo();
            if(meal != null){
                mealNF.add(extractMealNutitions(meal));
            }

            if(dayMeals != null){
                dayMealNF.add(extractDayMealNutiInfo(dayMeals));
            }

            if(mealPlan != null){
                planNF.add(extractDayMealNutiInfo(mealPlan.getMondayMeals()));
                planNF.add(extractDayMealNutiInfo(mealPlan.getTuesdayMeals()));
                planNF.add(extractDayMealNutiInfo(mealPlan.getWednesdayMeals()));
                planNF.add(extractDayMealNutiInfo(mealPlan.getThursdayMeals()));
                planNF.add(extractDayMealNutiInfo(mealPlan.getFridayMeals()));
                planNF.add(extractDayMealNutiInfo(mealPlan.getSaturdayMeals()));
                planNF.add(extractDayMealNutiInfo(mealPlan.getSundayMeals()));
            }
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onNutritionFetched(mealNF,dayMealNF,planNF);
                    }
                });

        } catch (final AppException e) {
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

    private NutitionInfo extractDayMealNutiInfo(DayMeals dayMeals) {
        final NutitionInfo dayMealNF = new NutitionInfo();
        if(dayMeals == null) return dayMealNF;
        List<Meal> meals = appRepository.getDayMealList(this, 0, dayMeals);
        for(Meal meal: meals){
            dayMealNF.add(extractMealNutitions(meal));
        }
        return dayMealNF;
    }

    private NutitionInfo extractMealNutitions(Meal meal) {
        final NutitionInfo mealNF = new NutitionInfo();
        if(meal == null) return mealNF;
       List<AddedFood> afs = meal.getAddedFoods();
        Long [] ids = Converters.foodIdsFromAddedFood(afs);
        List<Food> foods = appRepository.getFoodList(this, null, ids);
        Iterator<Food> it = foods.iterator();

        while (it.hasNext()){
            Food f = it.next();
            int serving = getServing(f,afs);
            NutitionInfo nf = f.extractNutritions();
            nf.applyServing(serving);
            mealNF.add(nf);
        }
        return mealNF;
    }

    private int getServing(Food f, List<AddedFood> afs) {
        for(AddedFood af: afs){
            if(f.getUid().longValue() == af.getFoodId().longValue()){
                return af.getServing();
            }
        }
        return 0;
    }
    @Override
    public void cancelCurrentRequest() {
        setCancelled(true);
    }


    @Override
    public void execute(Callback callback, boolean showLoader, Meal meal,DayMeals dayMeals, MealPlan mealPlan) {
        this.callback = callback;
        this.meal = meal;
        this.dayMeals = dayMeals;
        this.mealPlan = mealPlan;
        setCancelled(false);
        if (showLoader) showDialog(0,"Calculating Nutritions");
        getInteractorExecutor().performAction(this);
    }

}
