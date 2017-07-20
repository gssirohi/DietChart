package com.techticz.app.domain.repository.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.model.pojo.MealRoutine;

import java.util.List;

/**
 * Created by gssirohi on 10/6/17.
 */

@Dao
public interface MealPlanDao {

    @Query("SELECT * FROM " + MealPlan.TableName + "")
    List<MealPlan> getAllPlans();


    @Insert
    void insertAll(MealPlan... plans);

    @Insert
    void insertAll(List<MealPlan> plans);

    @Delete
    void delete(MealPlan plan);

    @Update
    int update(MealPlan plan);

    @Insert
    long insert(MealPlan plan);

    @Query("SELECT * FROM " + MealPlan.TableName + " WHERE uid IN (:uid)")
    MealPlan getMealPlan(Long uid);

    @Query("SELECT * FROM " + MealPlan.TableName + " WHERE creater LIKE :user")
    List<MealPlan> getPlansByCreater(String user);

    @Query("SELECT * FROM " + MealPlan.TableName + " WHERE name LIKE :searchKey")
    List<MealPlan> getAllContains(String searchKey);
}
