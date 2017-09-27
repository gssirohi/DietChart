package com.techticz.app.domain.repository;

import android.graphics.Bitmap;

import com.techticz.app.domain.interactor.CreateFoodInteractor;
import com.techticz.app.domain.interactor.CreateMealInteractor;
import com.techticz.app.domain.interactor.FetchBlobInteractor;
import com.techticz.app.domain.interactor.FetchMealDetailsInteractor;
import com.techticz.app.domain.interactor.FetchMealListInteractor;
import com.techticz.app.domain.interactor.LoginInteractor;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.executor.BaseInteractor;

import com.techticz.dietchart.backend.appUserApi.model.AppUser;
import com.techticz.dietchart.backend.appUserApi.model.UserLoginResponse;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by gssirohi on 5/7/16.
 */
public interface IAppRepository {

    public List<Meal> getMealList(BaseInteractor interactor, int dayIndex, String searchKey, long[] mealIds);

    public Meal getMealById(BaseInteractor interactor, Long id);

    public Meal getMealbyName(BaseInteractor interactor, String name);

    List<MealRoutine> getMealRoutinesByDay(BaseInteractor interactor, int day);

    long createMeal(BaseInteractor interactor, Meal meal);

    int addMealToRoutineWeek(BaseInteractor interactor, Integer id, int routineId, int day);

    long createMealPlan(BaseInteractor interactor, MealPlan plan,Boolean autoLoad,List<Integer> prefRoutines);

    MealPlan updateMealPlan(BaseInteractor interactor, MealPlan plan, boolean autoLoad, List<Integer> prefRoutines);

    MealPlan getMealPlan(BaseInteractor interactor, Long id);

    List<Meal> getDayMealList(BaseInteractor interactor, int day, DayMeals dayMeals);

    List<Food> getFoodList(BaseInteractor interactor, String searchKey, Long[] foodIds);

    Long createFood(BaseInteractor interactor, Food food);

    List<MealPlan> getMealPlans(BaseInteractor interactor, String searchKey, boolean isMyPlan);

    SystemHealth checkSystemHealth(BaseInteractor interactor);

    String getBlobUploadUrl(BaseInteractor interactor);

    ImageUploadResponse uploadImage(BaseInteractor interactor, Bitmap bitmap, String imageName);

    long updateFood(CreateFoodInteractor createFoodInteractor, Food food);

    Bitmap fetchBlob(BaseInteractor interactor, String blobKey, String servingUrl) throws MalformedURLException;


    long updateMeal(BaseInteractor interactor, Meal meal);

    Meal getMealDetails(BaseInteractor interactor, long mealId);

    UserLoginResponse login(LoginInteractor loginInteractor, AppUser appUser);

    void insertFoods(BaseInteractor interactor,List<Food> foods);

    void insertMeals(BaseInteractor interactor, List<Meal> mealList);

    void insertMealPlans(LoginInteractor loginInteractor, List<MealPlan> mealPlans);
}
