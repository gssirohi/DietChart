package com.techticz.dietchart.backend.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.List;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 26/8/17.
 */

@Entity
public class MealEntity {

    @Id
    private Long uid;
    private String name;
    private String desc;
    private Integer type;
    private Integer category;

    private String prefRoutine;

    private String addedFoods;

    private String blobServingUrl;
    private String blobKey;

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

    public String getPrefRoutine() {
        return prefRoutine;
    }

    public void setPrefRoutine(String prefRoutine) {
        this.prefRoutine = prefRoutine;
    }

    public String getAddedFoods() {
        return addedFoods;
    }

    public void setAddedFoods(String addedFoods) {
        this.addedFoods = addedFoods;
    }

    public String getBlobServingUrl() {
        return blobServingUrl;
    }

    public void setBlobServingUrl(String blobServingUrl) {
        this.blobServingUrl = blobServingUrl;
    }

    public String getBlobKey() {
        return blobKey;
    }

    public void setBlobKey(String blobKey) {
        this.blobKey = blobKey;
    }
}
