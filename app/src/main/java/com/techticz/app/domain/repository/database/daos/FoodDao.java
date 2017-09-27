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
public interface FoodDao {
    @Query("SELECT * FROM " + Food.TableName + "")
    List<Food> getAll();

    @Query("SELECT * FROM " + Food.TableName + " WHERE uid IN (:foodIds)")
    List<Food> loadAllByIds(Long[] foodIds);

    @Insert
    void insertAll(Food... foods);

    @Insert
    long insert(Food food);

    @Insert
    void insertAll(List<Food> foods);

    @Delete
    void delete(Food food);

    @Query("SELECT * FROM " + Food.TableName + " WHERE uid IN (:id)")
    Food getById(Integer id);

    @Query("SELECT * FROM " + Food.TableName + " WHERE name IN (:name)")
    Food getByName(String name);


    @Query("SELECT * FROM " + Food.TableName + " WHERE name LIKE :searchKey")
    List<Food> getAllContains(String searchKey);

    @Update
    int update(Food f);
}
