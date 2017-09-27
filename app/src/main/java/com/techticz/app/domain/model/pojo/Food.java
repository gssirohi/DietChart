package com.techticz.app.domain.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techticz.app.domain.model.Model;
import com.techticz.dietchart.backend.blobApi.model.ImageUploadResponse;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

import java.util.ArrayList;
import java.util.List;

import static com.techticz.app.domain.model.pojo.Food.TableName;


/**
 * Created by gssirohi on 9/7/17.
 */
@Parcel
@Entity(tableName = TableName)
public class Food extends Model {

    public Food() {
    }

    public final static String TableName = "foods";

     int category;
     int type;
     int servingId;
     int unitId;
     int contentPerServing;
     boolean eatable;

     List<Integer> richIn = new ArrayList<>();

    public static String getTableName() {
        return TableName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getServingId() {
        return servingId;
    }

    public void setServingId(int servingId) {
        this.servingId = servingId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getContentPerServing() {
        return contentPerServing;
    }

    public void setContentPerServing(int contentPerServing) {
        this.contentPerServing = contentPerServing;
    }

    public boolean isEatable() {
        return eatable;
    }

    public void setEatable(boolean eatable) {
        this.eatable = eatable;
    }

    public List<Integer> getRichIn() {
        return richIn;
    }

    public void setRichIn(List<Integer> richIn) {
        this.richIn = richIn;
    }
}
