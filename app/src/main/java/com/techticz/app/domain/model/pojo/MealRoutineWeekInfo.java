
package com.techticz.app.domain.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.techticz.app.domain.model.pojo.MealRoutineWeekInfo.TableName;


@Entity(tableName = TableName)
public class MealRoutineWeekInfo {

    public final static String TableName = "meal_routine_week";

    public MealRoutineWeekInfo(int routineId) {
        this.routineId = routineId;
    }

    @SerializedName("routine_id")
    @Expose
    @PrimaryKey
    @ColumnInfo(name = "routine_id")
    private int routineId;

    @ColumnInfo(name = "mon")
    private int monMealId;

    @ColumnInfo(name = "tue")
    private int tueMealId;

    @ColumnInfo(name = "wed")
    private int wedMealId;

    @ColumnInfo(name = "thu")
    private int thuMealId;

    @ColumnInfo(name = "fri")
    private int friMealId;

    @ColumnInfo(name = "sat")
    private int satMealId;

    @ColumnInfo(name = "sun")
    private int sunMealId;

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public int getMonMealId() {
        return monMealId;
    }

    public void setMonMealId(int monMealId) {
        this.monMealId = monMealId;
    }

    public int getTueMealId() {
        return tueMealId;
    }

    public void setTueMealId(int tueMealId) {
        this.tueMealId = tueMealId;
    }

    public int getWedMealId() {
        return wedMealId;
    }

    public void setWedMealId(int wedMealId) {
        this.wedMealId = wedMealId;
    }

    public int getThuMealId() {
        return thuMealId;
    }

    public void setThuMealId(int thuMealId) {
        this.thuMealId = thuMealId;
    }

    public int getFriMealId() {
        return friMealId;
    }

    public void setFriMealId(int friMealId) {
        this.friMealId = friMealId;
    }

    public int getSatMealId() {
        return satMealId;
    }

    public void setSatMealId(int satMealId) {
        this.satMealId = satMealId;
    }

    public int getSunMealId() {
        return sunMealId;
    }

    public void setSunMealId(int sunMealId) {
        this.sunMealId = sunMealId;
    }

    public void setMealIds(int mon, int tue, int wed, int thu, int fri, int sat, int sun) {
        setMonMealId(mon);
        setTueMealId(tue);
        setWedMealId(wed);
        setThuMealId(thu);
        setFriMealId(fri);
        setSatMealId(sat);
        setSunMealId(sun);
    }

    public int getMealIdByDay(int day) {
        switch (day) {
            case 0:
                return getMonMealId();
            case 1:
                return getTueMealId();
            case 2:
                return getWedMealId();
            case 3:
                return getThuMealId();
            case 4:
                return getFriMealId();
            case 5:
                return getSatMealId();
            case 6:
                return getSunMealId();
        }
        return 0;
    }

    public void setMealIdForDay(Integer mealId, int day) {
        switch (day) {
            case 0:
                setMonMealId(mealId);
                break;
            case 1:
                setTueMealId(mealId);
                break;
            case 2:
                setWedMealId(mealId);
                break;
            case 3:
                setThuMealId(mealId);
                break;
            case 4:
                setFriMealId(mealId);
                break;
            case 5:
                setSatMealId(mealId);
                break;
            case 6:
                setSunMealId(mealId);
                break;

        }
        return;
    }
}
