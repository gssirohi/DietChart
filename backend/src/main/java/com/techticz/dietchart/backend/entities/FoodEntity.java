package com.techticz.dietchart.backend.entities;

import com.google.appengine.repackaged.com.google.appengine.api.search.SearchServicePb;
import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gssirohi on 9/7/17.
 */

@Entity
public class FoodEntity  extends Model{

    int category;
    @Index
    int type;
    int servingId;
    int unitId;
    int contentPerServing;
    boolean eatable;

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

}
