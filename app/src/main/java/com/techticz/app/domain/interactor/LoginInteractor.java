package com.techticz.app.domain.interactor;

import android.content.Context;
import android.text.TextUtils;

import com.techticz.app.base.AppCore;
import com.techticz.app.constant.Repositories;
import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.domain.repository.api.APIResponseMapper;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;
import com.techticz.app.utility.AppUtils;
import com.techticz.dietchart.backend.appUserApi.model.AppUser;
import com.techticz.dietchart.backend.appUserApi.model.FoodEntity;
import com.techticz.dietchart.backend.appUserApi.model.MarketResponse;
import com.techticz.dietchart.backend.appUserApi.model.MealEntity;
import com.techticz.dietchart.backend.appUserApi.model.MealPlanEntityItem;
import com.techticz.dietchart.backend.appUserApi.model.UserLoginResponse;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by gssirohi on 15/7/16.
 */
public class LoginInteractor extends BaseInteractor implements LoginUseCase {

    private IAppRepository appRepository;
    private Callback callback;
    private AppUser appUser;

    public LoginInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            Thread.sleep(5*1000);
             final UserLoginResponse loginResponse = appRepository.login(this,appUser);
            MarketResponse market = loginResponse.getMarket();
            IAppRepository repository = AppCore.getInstance().getProvider().getAppRepository(Repositories.DATABASE);
            APIResponseMapper mapper = new APIResponseMapper();
           // setDialogMessage("Bringing foods for you..");
            List<FoodEntity> foodEntities = new ArrayList<>();
            if(market.getExclusiveFood() != null) {
                foodEntities = market.getExclusiveFood().getItems();
            }
            List<Food> foods = mapper.getFoodsFromUserFoodEntity(foodEntities);
            try {
                repository.insertFoods(this, foods);
            } catch (Exception e) {
                e.printStackTrace();
            }

          //  setDialogMessage("Preparing Meals for you..");
            List<MealEntity> mealEntities = new ArrayList<>();
            if(market.getExclusiveMeals() != null) {
                mealEntities = market.getExclusiveMeals().getItems();
            }
            List<Meal> mealList = mapper.getMealsFromUserMealEntity(mealEntities);
         try {
            repository.insertMeals(this,mealList);
        } catch (Exception e) {
            e.printStackTrace();
        }
            List<MealPlanEntityItem> planEntities = new ArrayList<>();
        if(market.getPlans() != null) {
            planEntities = market.getPlans().getItems();
        }
        final List<MealPlan> mealPlans = mapper.getMealPlansFromUserEntities(planEntities);
         try {
            repository.insertMealPlans(this,mealPlans);
        } catch (Exception e) {
            e.printStackTrace();
        }
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoggedIn(loginResponse,mealPlans);
                    }
                });

        } catch (final AppException e) {
            AppLogger.e(this, "Error on user login");
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
    public void execute(Callback callback, AppUser appUser, boolean showLoader) {
        this.callback = callback;
        this.appUser = appUser;
        if (showLoader) {
            showDialog("Bringing Food Market ... ");
        }
        getInteractorExecutor().performAction(this);
    }
}
