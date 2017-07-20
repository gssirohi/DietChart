
package com.techticz.app.domain.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.Model;
import com.techticz.app.domain.model.ProductModel;

import java.util.List;

@Entity(tableName = Meal.TableName)
public class Meal extends Model {

    public final static String TableName = "meals";
    @SerializedName("uid")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private Long uid;


    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("desc")
    @Expose
    private String desc;

    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("category")
    @Expose
    private Integer category;

    @SerializedName("prefRoutine")
    @Expose
    private List<Integer> prefRoutine;

    @SerializedName("foodIds")
    @Expose
    private List<Long> foodIds;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public List<Integer> getPrefRoutine() {
        return prefRoutine;
    }

    public void setPrefRoutine(List<Integer> prefRoutine) {
        this.prefRoutine = prefRoutine;
    }

    public List<Long> getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(List<Long> foodIds) {
        this.foodIds = foodIds;
    }
}
