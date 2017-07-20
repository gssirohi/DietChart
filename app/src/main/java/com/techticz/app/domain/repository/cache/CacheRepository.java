package com.techticz.app.domain.repository.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.techticz.app.domain.interactor.CreateFoodInteractor;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.network.ResponseContainer;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;

import java.util.List;

/**
 * Created by gssirohi on 4/9/16.
 */
public class CacheRepository implements IAppRepository {


    private LruCache<String, Bitmap> mImageMemoryCache;
    private LruCache<String, ResponseContainer> mResponseMemoryCache;

    public CacheRepository() {
        mImageMemoryCache = getImageCacheMemory();
        mResponseMemoryCache = getResponseCacheMemory();
    }

    public LruCache<String, Bitmap> getImageCacheMemory() {
        if (mImageMemoryCache != null) return mImageMemoryCache;
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mImageMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
        return mImageMemoryCache;
    }


    public LruCache<String, ResponseContainer> getResponseCacheMemory() {
        if (mResponseMemoryCache != null) return mResponseMemoryCache;
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mResponseMemoryCache = new LruCache<String, ResponseContainer>(cacheSize) {
            @Override
            protected int sizeOf(String key, ResponseContainer responseContainer) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return 10 * 1024 / 1024;
            }
        };
        return mResponseMemoryCache;
    }


    public void addResponseToMemoryCache(String key, ResponseContainer container) {
        if (getResponseFromMemCache(key) == null) {
            mResponseMemoryCache.put(key, container);
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mImageMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mImageMemoryCache.get(key);
    }

    public ResponseContainer getResponseFromMemCache(String key) {
        return mResponseMemoryCache.get(key);
    }

    @Override
    public List<Meal> getMealList(BaseInteractor interactor, int dayIndex, String searchKey, int[] mealIds) {
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
}