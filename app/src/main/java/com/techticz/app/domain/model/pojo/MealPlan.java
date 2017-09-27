package com.techticz.app.domain.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.Model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import static com.techticz.app.domain.model.pojo.MealPlan.TableName;

/**
 * Created by gssirohi on 8/7/17.
 */

@Parcel
@Entity(tableName = TableName)
public class MealPlan extends Model {
    public final static String TableName = "meal_plans";

    public MealPlan() {
    }

     int healthGoal;
     float dailyCalory;

    DayMeals mondayMeals = new DayMeals();

     DayMeals tuesdayMeals = new DayMeals();

     DayMeals wednesdayMeals = new DayMeals();

     DayMeals thursdayMeals = new DayMeals();

     DayMeals fridayMeals = new DayMeals();

     DayMeals saturdayMeals = new DayMeals();

     DayMeals sundayMeals = new DayMeals();


    public static String getTableName() {
        return TableName;
    }


    public float getDailyCalory() {
        return dailyCalory;
    }

    public void setDailyCalory(float dailyCalory) {
        this.dailyCalory = dailyCalory;
    }

    public DayMeals getMondayMeals() {
        return mondayMeals;
    }

    public void setMondayMeals(DayMeals mondayMeals) {
        this.mondayMeals = mondayMeals;
    }

    public DayMeals getTuesdayMeals() {
        return tuesdayMeals;
    }

    public void setTuesdayMeals(DayMeals tuesdayMeals) {
        this.tuesdayMeals = tuesdayMeals;
    }

    public DayMeals getWednesdayMeals() {
        return wednesdayMeals;
    }

    public void setWednesdayMeals(DayMeals wednesdayMeals) {
        this.wednesdayMeals = wednesdayMeals;
    }

    public DayMeals getThursdayMeals() {
        return thursdayMeals;
    }

    public void setThursdayMeals(DayMeals thursdayMeals) {
        this.thursdayMeals = thursdayMeals;
    }

    public DayMeals getFridayMeals() {
        return fridayMeals;
    }

    public void setFridayMeals(DayMeals fridayMeals) {
        this.fridayMeals = fridayMeals;
    }

    public DayMeals getSaturdayMeals() {
        return saturdayMeals;
    }

    public void setSaturdayMeals(DayMeals saturdayMeals) {
        this.saturdayMeals = saturdayMeals;
    }

    public DayMeals getSundayMeals() {
        return sundayMeals;
    }

    public void setSundayMeals(DayMeals sundayMeals) {
        this.sundayMeals = sundayMeals;
    }

    public int getHealthGoal() {
        return healthGoal;
    }

    public void setHealthGoal(int healthGoal) {
        this.healthGoal = healthGoal;
    }
}
