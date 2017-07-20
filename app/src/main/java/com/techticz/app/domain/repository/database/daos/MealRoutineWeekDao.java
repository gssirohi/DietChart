package com.techticz.app.domain.repository.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.domain.model.pojo.MealRoutineWeekInfo;
import com.techticz.app.utility.AppUtils;

import java.util.List;

/**
 * Created by gssirohi on 10/6/17.
 */

@Dao
public interface MealRoutineWeekDao {

    @Query("SELECT * FROM " + MealRoutineWeekInfo.TableName + "")
    List<MealRoutineWeekInfo> getRoutineWeekInfo();

    @Query("SELECT * FROM " + MealRoutineWeekInfo.TableName + " WHERE routine_id IN (:routine_id)")
    MealRoutineWeekInfo getRoutineWeekByRoutineId(int routine_id);

    @Insert
    void insertAll(MealRoutineWeekInfo... weekInfo);

    @Delete
    void delete(MealRoutineWeekInfo weekInfo);

    @Insert
    void insertAll(List<MealRoutineWeekInfo> weekInfos);

    @Update
    int update(MealRoutineWeekInfo mealRoutineWeekInfo);

}
