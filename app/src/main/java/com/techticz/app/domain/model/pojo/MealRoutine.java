
package com.techticz.app.domain.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.Model;

import static com.techticz.app.domain.model.pojo.MealRoutine.TableName;

@Entity(tableName = TableName)
public class MealRoutine  {

    public final static String TableName = "meal_routine";

    @SerializedName("uid")
    @Expose
    @PrimaryKey
    private int uid;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("desc")
    @Expose
    @ColumnInfo(name = "desc")
    private String desc;

    @Ignore
    private Meal meal;
    @Ignore
    private boolean eaten;
    @Ignore
    private boolean missed;


    public MealRoutine(int uid, String name, String desc) {
        this.uid = uid;
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Meal getMeal() {
        return meal;
    }

    @Override
    public String toString(){
        return "RName:"+name+",ID:"+uid+",Meal:"+meal;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setMissed(boolean missed) {
        this.missed = missed;
    }

    public boolean isMissed() {
        return missed;
    }
}
