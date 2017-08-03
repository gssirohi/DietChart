package com.techticz.app.domain.repository.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;

import java.util.List;

/**
 * Created by gssirohi on 10/6/17.
 */

@Dao
public interface MealDao {
    @Query("SELECT * FROM " + Meal.TableName)
    List<Meal> getAll();

    @Query("SELECT * FROM " + Meal.TableName + " WHERE uid IN (:mealIds)")
    List<Meal> loadAllByIds(long[] mealIds);

    @Insert
    void insertAll(Meal... meals);

    @Insert
    long insert(Meal meal);

    @Insert
    void insertAll(List<Meal> meals);

    @Delete
    void delete(Meal meal);

    @Query("SELECT * FROM " + Meal.TableName + " WHERE uid IN (:id)")
    Meal getById(Long id);

    @Query("SELECT * FROM " + Meal.TableName + " WHERE name IN (:name)")
    Meal getByName(String name);

    @Query("SELECT * FROM " + Meal.TableName + " WHERE name LIKE :searchKey")
    List<Meal> getAllContains(String searchKey);

    @Update
    int update(Meal meal);
}
