package com.techticz.app.domain.repository.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techticz.app.constant.AppConstants;
import com.techticz.app.constant.Routines;
import com.techticz.app.domain.interactor.CreateFoodInteractor;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.domain.model.pojo.MealRoutineWeekInfo;
import com.techticz.app.domain.model.pojo.Nutrition;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.utility.CommonUtils;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;
import com.techticz.dietchart.backend.foodEntityApi.model.FoodEntity;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gssirohi on 25/8/16.
 */
public class DataBaseRepository implements IAppRepository {


    private DietChartDataBase db;

    public DataBaseRepository(Context appContext) {
        db = Room.databaseBuilder(appContext,
                DietChartDataBase.class, AppConstants.DATABASE_NAME).build();
        initializeDatabase(appContext);
    }

    private void initializeDatabase(final Context context) {

        //initialize data into tables
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<MealRoutine> routines = db.mealRoutineDao().getAllRoutine();

                if (routines == null || routines.isEmpty()) {
                    //  insertDefaultMeals(context);
                    insertDefaultRoutines(context);
                    //insertRoutinesWeek(context);
                }
            }
        });
        thread.start();

    }

    private void insertRoutinesWeek(Context context) {
        List<MealRoutine> routines = db.mealRoutineDao().getAllRoutine();
        List<MealRoutineWeekInfo> weekInfos = new ArrayList<>();
        int r = 0;
        for (MealRoutine routine : routines) {
            MealRoutineWeekInfo info = new MealRoutineWeekInfo(routine.getUid());
            if (r++ < 3) {
                info.setMealIds(getRandomMealId(), getRandomMealId(), getRandomMealId(), getRandomMealId(), getRandomMealId()
                        , getRandomMealId(), getRandomMealId());
            }
            weekInfos.add(info);
        }
        db.mealRoutineWeekDao().insertAll(weekInfos);

    }

    private int getRandomMealId() {
        List<Meal> meals = db.mealDao().getAll();
        int total = meals.size();
        int random = CommonUtils.randomNumber(0, total - 1);
        return meals.get(random).getUid().intValue();
    }

    private void insertDefaultMeals(Context context) {
        List<Meal> meals = new ArrayList<>();
        //---------Early morning------------------//

        db.mealDao().insertAll(meals);
    }

    private void insertDefaultRoutines(Context context) {
        List<MealRoutine> routines = new ArrayList<>();
        List<Routines> list = Routines.getAll();
        for (Routines r : list) {
            routines.add(new MealRoutine(r.code, r.lable, r.desc));
        }
        db.mealRoutineDao().insertAll(routines);
    }

    @Override
    public List<Meal> getMealList(BaseInteractor interactor, int limit, String searchKey, long[] mealIds) {
        if (!TextUtils.isEmpty(searchKey))
            return db.mealDao().getAllContains("%" + searchKey + "%");
        else if (mealIds.length > 0) {
            return db.mealDao().loadAllByIds(mealIds);
        } else {
            return db.mealDao().getAll();
        }
    }

    @Override
    public Meal getMealById(BaseInteractor interactor, Long id) {
        return db.mealDao().getById(id);
    }

    @Override
    public Meal getMealbyName(BaseInteractor interactor, String name) {
        return db.mealDao().getByName(name);
    }

    @Override
    public List<MealRoutine> getMealRoutinesByDay(BaseInteractor interactor, int day) {
        List<MealRoutine> routines = db.mealRoutineDao().getAllRoutine();
        return routines;
    }


    @Override
    public int addMealToRoutineWeek(BaseInteractor interactor, Integer mealId, int routineId, int day) {
        MealRoutineWeekInfo info = db.mealRoutineWeekDao().getRoutineWeekByRoutineId(routineId);
        info.setMealIdForDay(mealId, day);
        return (int) db.mealRoutineWeekDao().update(info);
    }

    @Override
    public long createMealPlan(BaseInteractor interactor, MealPlan plan) {
        long id = 0;
        id =  db.mealPlanDao().insert(plan);
        return id;
    }

    @Override
    public int updateMealPlan(BaseInteractor interactor, MealPlan plan) {

        return (int) db.mealPlanDao().update(plan);

    }

    @Override
    public MealPlan getMealPlan(BaseInteractor interactor, Long id) {
        return db.mealPlanDao().getMealPlan(id);
    }

    @Override
    public List<Meal> getDayMealList(BaseInteractor interactor, int day, DayMeals dayMeals) {
        ArrayList<Meal> meals = new ArrayList<>();
        meals.add(db.mealDao().getById(dayMeals.getR1()));
        meals.add(db.mealDao().getById(dayMeals.getR2()));
        meals.add(db.mealDao().getById(dayMeals.getR3()));
        meals.add(db.mealDao().getById(dayMeals.getR4()));
        meals.add(db.mealDao().getById(dayMeals.getR5()));
        meals.add(db.mealDao().getById(dayMeals.getR6()));
        meals.add(db.mealDao().getById(dayMeals.getR7()));

        return meals;
    }

    @Override
    public List<Food> getFoodList(BaseInteractor interactor, String searchKey, Long[] foodIds) {
        if (!TextUtils.isEmpty(searchKey))
            return db.foodDao().getAllContains("%" + searchKey + "%");
        else if (foodIds.length > 0) {
            return db.foodDao().loadAllByIds(foodIds);
        } else {
            return db.foodDao().getAll();
        }
    }

    @Override
    public Long createFood(BaseInteractor interactor, Food food) {
        return  db.foodDao().insert(food);
    }

    @Override
    public List<MealPlan> getMealPlans(BaseInteractor interactor, String searchKey, boolean isMyPlan) {
        if (isMyPlan) {
            return db.mealPlanDao().getPlansByCreater("user");
        } else {
            return db.mealPlanDao().getAllContains("%" + searchKey + "%");
        }
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
        Map config = new HashMap();
        config.put("cloud_name", AppConstants.CLOUDINARY_CLOUD_NAME);
        config.put("api_key", AppConstants.CLOUDINARY_API_KEY);
        config.put("api_secret", AppConstants.CLOUDINARY_API_SECRET);
        Cloudinary cloudinary = new Cloudinary(config);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);

        try {
            cloudinary.uploader().upload(bs, ObjectUtils.asMap("public_id", imageName));
            String imageUrl = cloudinary.url().generate(imageName + ".jpg");
            ImageUploadResponse resp  = new ImageUploadResponse();
            resp.setBlobKey("");
            resp.setServingUrl(imageUrl);
            return resp;
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public long updateMeal(BaseInteractor i, Meal meal) {
        int row = db.mealDao().update(meal);
        return meal.getUid();
    }

    @Override
    public Meal getMealDetails(BaseInteractor i, long mealId) {
        Meal meal = db.mealDao().getById(mealId);
        return meal;
    }

    @Override
    public long createMeal(BaseInteractor interactor, Meal meal) {
        long id =  db.mealDao().insert(meal);
        return id;
    }

    private void addDummyNutritions(Meal meal) {
        if (meal == null) return;
        List<Nutrition> nutritions = new ArrayList<>();
        nutritions.add(new Nutrition("Carbs", "", "gm", 70));
        nutritions.add(new Nutrition("Fat", "", "gm", 40));
        nutritions.add(new Nutrition("Amino Acid", "", "ml", 35));
        nutritions.add(new Nutrition("Vitamin D", "", "mn", 30));
        //   meal.setNutritions(nutritions);
    }
}
