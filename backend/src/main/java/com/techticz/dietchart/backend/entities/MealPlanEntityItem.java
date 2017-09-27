package com.techticz.dietchart.backend.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

import ch.boye.httpclientandroidlib.util.TextUtils;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 29/8/17.
 */
@Entity
public class MealPlanEntityItem  extends Model{
    String mondayMeals;

    String tuesdayMeals;

    String wednesdayMeals;
    String thursdayMeals;

    String fridayMeals;
    String saturdayMeals;
    String sundayMeals;

    int healthGoal;
    float dailyCalory;

    private String mutualMealIds;

    public String getMondayMeals() {
        return mondayMeals;
    }

    public void setMondayMeals(String mondayMeals) {
        this.mondayMeals = mondayMeals;
    }

    public String getTuesdayMeals() {
        return tuesdayMeals;
    }

    public void setTuesdayMeals(String tuesdayMeals) {
        this.tuesdayMeals = tuesdayMeals;
    }

    public String getWednesdayMeals() {
        return wednesdayMeals;
    }

    public void setWednesdayMeals(String wednesdayMeals) {
        this.wednesdayMeals = wednesdayMeals;
    }

    public String getThursdayMeals() {
        return thursdayMeals;
    }

    public void setThursdayMeals(String thursdayMeals) {
        this.thursdayMeals = thursdayMeals;
    }

    public String getFridayMeals() {
        return fridayMeals;
    }

    public void setFridayMeals(String fridayMeals) {
        this.fridayMeals = fridayMeals;
    }

    public String getSaturdayMeals() {
        return saturdayMeals;
    }

    public void setSaturdayMeals(String saturdayMeals) {
        this.saturdayMeals = saturdayMeals;
    }

    public String getSundayMeals() {
        return sundayMeals;
    }

    public void setSundayMeals(String sundayMeals) {
        this.sundayMeals = sundayMeals;
    }


    public List<Long> getMutualMealIds() {
        List<Long> exclusive = new ArrayList<>();
        List<Long> daymeals;
        daymeals = stringtoLongList(getMondayMeals());
        exclusive.removeAll(daymeals);
        exclusive.addAll(daymeals);
        daymeals = stringtoLongList(getTuesdayMeals());
        exclusive.removeAll(daymeals);
        exclusive.addAll(daymeals);
        daymeals = stringtoLongList(getWednesdayMeals());
        exclusive.removeAll(daymeals);
        exclusive.addAll(daymeals);
        daymeals = stringtoLongList(getThursdayMeals());
        exclusive.removeAll(daymeals);
        exclusive.addAll(daymeals);
        daymeals = stringtoLongList(getFridayMeals());
        exclusive.removeAll(daymeals);
        exclusive.addAll(daymeals);
        daymeals = stringtoLongList(getSaturdayMeals());
        exclusive.removeAll(daymeals);
        exclusive.addAll(daymeals);
        daymeals = stringtoLongList(getSundayMeals());
        exclusive.removeAll(daymeals);
        exclusive.addAll(daymeals);
        return exclusive;
    }

    public static List<Long> stringtoLongList(String str) {
        List<Long> list = new ArrayList<>();
        if(TextUtils.isEmpty(str)) return list;

        String[] ids = str.split(":");

        for (String num : ids) {
            try {
                list.add(Long.parseLong(num));
            } catch (Exception e) {

            }
        }
        return list;
    }

    public void setMutualMealIds(String mutualMealIds) {
        this.mutualMealIds = mutualMealIds;
    }
    public int getHealthGoal() {
        return healthGoal;
    }

    public void setHealthGoal(int healthGoal) {
        this.healthGoal = healthGoal;
    }
    public float getDailyCalory() {
        return dailyCalory;
    }

    public void setDailyCalory(float dailyCalory) {
        this.dailyCalory = dailyCalory;
    }


}

