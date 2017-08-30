package com.techticz.dietchart.backend.entities;

import android.arch.persistence.room.Embedded;

import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 29/8/17.
 */
@Entity
public class MealPlanEntityItem {
    public final static String TableName = "meal_plans";

    public MealPlanEntityItem() {
    }

    @SerializedName("uid")
    @Expose
    @Id
    Long uid;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("desc")
    @Expose
    String desc;

    String blobServingUrl;

    @SerializedName("healthGoal")
    @Expose
    int healthGoal;

    @SerializedName("creater")
    @Expose
    String creater;

    @SerializedName("dailycalory")
    @Expose
    float dailyCalory;

    @SerializedName("mondayMeals")
    @Expose
    @Embedded
    String mondayMeals;

    @SerializedName("tuesdayMeals")
    @Expose
    @Embedded
    String tuesdayMeals;

    @SerializedName("wednesdayMeals")
    @Expose
    @Embedded
    String wednesdayMeals;

    @SerializedName("thursdayMeals")
    @Expose
    
    String thursdayMeals;

    @SerializedName("fridayMeals")
    @Expose
    @Embedded

    String fridayMeals;

    @SerializedName("saturdayMeals")
    @Expose
    @Embedded
    String saturdayMeals;

    @SerializedName("sundayMeals")
    @Expose
    @Embedded
    String sundayMeals;


    public static String getTableName() {
        return TableName;
    }

    public String getBlobServingUrl() {
        return blobServingUrl;
    }

    public void setBlobServingUrl(String blobServingUrl) {
        this.blobServingUrl = blobServingUrl;
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

