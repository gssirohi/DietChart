package com.techticz.app.domain.repository.mock;

import android.graphics.Bitmap;

import com.techticz.app.domain.interactor.CreateFoodInteractor;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by gssirohi on 5/7/16.
 */
public class MockRepository implements IAppRepository {
    @Override
    public List<Meal> getMealList(BaseInteractor interactor, int dayIndex, String searchKey, long[] mealIds) {
        return null;
    }

    @Override
    public Meal getMealById(BaseInteractor interactor, Long id) {
        return null;
    }

    @Override
    public Meal getMealbyName(BaseInteractor interactor, String name) {
        return null;
    }

    @Override
    public List<MealRoutine> getMealRoutinesByDay(BaseInteractor interactor, int day) {
        return null;
    }

    @Override
    public int createMeal(BaseInteractor interactor, Meal meal) {
        return 0;
    }

    @Override
    public int addMealToRoutineWeek(BaseInteractor interactor, Integer id, int routineId, int day) {
        return 0;
    }

    @Override
    public long createMealPlan(BaseInteractor interactor, MealPlan plan) {
        return 0;
    }

    @Override
    public int updateMealPlan(BaseInteractor interactor, MealPlan plan) {
        return 0;
    }

    @Override
    public MealPlan getMealPlan(BaseInteractor interactor, Long id) {
        return null;
    }

    @Override
    public List<Meal> getDayMealList(BaseInteractor interactor, int day, DayMeals dayMeals) {
        return null;
    }

    @Override
    public List<Food> getFoodList(BaseInteractor interactor, String searchKey, Long[] foodIds) {
        return null;
    }

    @Override
    public Long createFood(BaseInteractor interactor, Food food) {
        return 0l;
    }

    @Override
    public List<MealPlan> getMealPlans(BaseInteractor interactor, String searchKey, boolean isMyPlan) {
        return null;
    }

    @Override
    public SystemHealth checkSystemHealth(BaseInteractor interactor) {
        return null;
    }

    @Override
    public String getBlobUploadUrl(BaseInteractor interactor) {
        return null;
    }

    @Override
    public ImageUploadResponse uploadImage(BaseInteractor interactor, Bitmap bitmap, String imageName) {
        return null;
    }

    @Override
    public long updateFood(CreateFoodInteractor createFoodInteractor, Food food) {
        return 0;
    }

    @Override
    public Bitmap fetchBlob(BaseInteractor interactor, String blobKey,String servingurl) throws MalformedURLException {
        return null;
    }

    @Override
    public long updateMeal(BaseInteractor interactor, Meal meal) {
        return 0;
    }
}
