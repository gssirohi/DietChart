package com.techticz.app.domain.repository.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealRoutine;

import java.util.List;

/**
 * Created by gssirohi on 10/6/17.
 */

@Dao
public interface MealRoutineDao {

    @Query("SELECT * FROM " + MealRoutine.TableName + "")
    List<MealRoutine> getAllRoutine();


    @Insert
    void insertAll(MealRoutine... routines);

    @Insert
    void insertAll(List<MealRoutine> routines);

    @Delete
    void delete(MealRoutine routine);

}
