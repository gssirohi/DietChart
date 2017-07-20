package com.techticz.app.domain.repository.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.domain.model.pojo.MealRoutineWeekInfo;
import com.techticz.app.domain.repository.database.daos.FoodDao;
import com.techticz.app.domain.repository.database.daos.MealDao;
import com.techticz.app.domain.repository.database.daos.MealPlanDao;
import com.techticz.app.domain.repository.database.daos.MealRoutineDao;
import com.techticz.app.domain.repository.database.daos.MealRoutineWeekDao;

/**
 * Created by gssirohi on 10/6/17.
 */

@Database(
        entities = {
                Food.class,
                Meal.class,
                MealRoutine.class,
                MealRoutineWeekInfo.class,
                MealPlan.class
        }
        , version = 1)
@TypeConverters({Converters.class})
public abstract class DietChartDataBase extends RoomDatabase {
    public abstract FoodDao foodDao();

    public abstract MealPlanDao mealPlanDao();

    public abstract MealDao mealDao();

    public abstract MealRoutineDao mealRoutineDao();

    public abstract MealRoutineWeekDao mealRoutineWeekDao();
}
