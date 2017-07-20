package com.techticz.app.domain.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.Model;

import java.util.ArrayList;
import java.util.List;

import static com.techticz.app.domain.model.pojo.MealPlan.TableName;

/**
 * Created by gssirohi on 8/7/17.
 */

@Entity(tableName = TableName)
public class MealPlan extends Model {
    public final static String TableName = "meal_plans";


    @SerializedName("uid")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private Long uid;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("desc")
    @Expose
    @ColumnInfo(name = "desc")
    private String desc;

    @SerializedName("healthGoal")
    @Expose
    @ColumnInfo(name = "healthGoal")
    private int healthGoal;

    @SerializedName("creater")
    @Expose
    @ColumnInfo(name = "creater")
    private String creater;

    @SerializedName("dailycalory")
    @Expose
    @ColumnInfo(name = "dailycalory")
    private float dailyCalory;

    @SerializedName("mondayMeals")
    @Expose
    private DayMeals mondayMeals = new DayMeals();

    @SerializedName("tuesdayMeals")
    @Expose
    private DayMeals tuesdayMeals = new DayMeals();

    @SerializedName("wednesdayMeals")
    @Expose
    private DayMeals wednesdayMeals = new DayMeals();

    @SerializedName("thursdayMeals")
    @Expose
    private DayMeals thursdayMeals = new DayMeals();

    @SerializedName("fridayMeals")
    @Expose

    private DayMeals fridayMeals = new DayMeals();

    @SerializedName("saturdayMeals")
    @Expose

    private DayMeals saturdayMeals = new DayMeals();

    @SerializedName("sundayMeals")
    @Expose

    private DayMeals sundayMeals = new DayMeals();


    public static String getTableName() {
        return TableName;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }
}