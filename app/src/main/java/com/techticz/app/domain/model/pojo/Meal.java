
package com.techticz.app.domain.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.Model;
import com.techticz.app.domain.model.ProductModel;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = Meal.TableName)
@Parcel
public class Meal extends Model {

    public final static String TableName = "meals";

    public Meal() {
    }

    @SerializedName("uid")
    @Expose
    @PrimaryKey(autoGenerate = true)
     Long uid;


    @SerializedName("name")
    @Expose
     String name;

    @SerializedName("desc")
    @Expose
     String desc;

    @SerializedName("type")
    @Expose
     Integer type;

    @SerializedName("category")
    @Expose
     Integer category;

    @SerializedName("prefRoutine")
    @Expose
     List<Integer> prefRoutine;

    @SerializedName("addedFoods")
    @Expose
     List<AddedFood> addedFoods;

    @SerializedName("foodIds")
    @Expose
     List<Long> foodIds;
    @Ignore
     Bitmap bitmap;
     String blobServingUrl;
     String blobKey;

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

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBlobServingUrl(String blobServingUrl) {
        this.blobServingUrl = blobServingUrl;
    }

    public String getBlobServingUrl() {
        return blobServingUrl;
    }

    public void setBlobKey(String blobKey) {
        this.blobKey = blobKey;
    }

    public String getBlobKey() {
        return blobKey;
    }

    public List<AddedFood> getAddedFoods() {
        if(addedFoods == null) addedFoods = new ArrayList<>();
        return addedFoods;
    }

    public void setAddedFoods(List<AddedFood> addedFoods) {
        this.addedFoods = addedFoods;
    }
}
