package com.techticz.dietchart.backend.entities;

import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gssirohi on 9/7/17.
 */

@Entity
public class FoodEntity  {
    public final static String TableName = "foods";

    @SerializedName("uid")
    @Expose
    @Id
    private Long uid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("desc")
    @Expose
    private String desc;

    @SerializedName("category")
    @Expose
    private int category;

    @SerializedName("creator")
    @Expose
    private String creator;

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("servingId")
    @Expose
    private int servingId;

    @SerializedName("unitId")
    @Expose
    private int unitId;

    @SerializedName("contentPerServing")
    @Expose
    private int contentPerServing;


    @SerializedName("calory")
    @Expose
    private float calory;

    @SerializedName("carbs")
    @Expose
    private float carbs;

    @SerializedName("fat")
    @Expose
    private float fat;

    @SerializedName("fiber")
    @Expose
    private float fiber;

    @SerializedName("protine")
    @Expose
    private float protine;

    @SerializedName("vitaminA")
    @Expose
    private float vitaminA;

    @SerializedName("vitaminB")
    @Expose
    private float vitaminB;

    @SerializedName("vitaminC")
    @Expose
    private float vitaminC;

    @SerializedName("vitamind")
    @Expose
    private float vitaminD;

    @SerializedName("vitaminE")
    @Expose
    private float vitaminE;

    @SerializedName("vitaminK")
    @Expose
    private float vitaminK;

    @SerializedName("potassium")
    @Expose
    private float potassium;

    @SerializedName("calcium")
    @Expose
    private float calcium;

    @SerializedName("sodium")
    @Expose
    private float sodium;

    @SerializedName("iron")
    @Expose
    private float iron;

    @SerializedName("magnissium")
    @Expose
    private float magnissium;

    @SerializedName("zinc")
    @Expose
    private float zinc;

    @SerializedName("cholestrol")
    @Expose
    private float cholestrol;

    @SerializedName("sugar")
    @Expose
    private float sugar;


    @SerializedName("eatable")
    @Expose
    private boolean eatable;

    @SerializedName("richIn")
    @Expose
    private String richIn;

    @SerializedName("prefRoutine")
    @Expose
    private String prefRoutine;


    @SerializedName("verified")
    @Expose
    private boolean verified;

    @SerializedName("blobServingUrl")
    @Expose
    private String blobServingUrl;

    @SerializedName("blobKey")
    @Expose
    private String blobKey;

    public static String getTableName() {
        return TableName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
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

    public float getCalory() {
        return calory;
    }

    public void setCalory(float calory) {
        this.calory = calory;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public float getProtine() {
        return protine;
    }

    public void setProtine(float protine) {
        this.protine = protine;
    }

    public float getVitaminA() {
        return vitaminA;
    }

    public void setVitaminA(float vitaminA) {
        this.vitaminA = vitaminA;
    }

    public float getVitaminB() {
        return vitaminB;
    }

    public void setVitaminB(float vitaminB) {
        this.vitaminB = vitaminB;
    }

    public float getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(float vitaminC) {
        this.vitaminC = vitaminC;
    }

    public float getPotassium() {
        return potassium;
    }

    public void setPotassium(float potassium) {
        this.potassium = potassium;
    }

    public float getCalcium() {
        return calcium;
    }

    public void setCalcium(float calcium) {
        this.calcium = calcium;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public float getIron() {
        return iron;
    }

    public void setIron(float iron) {
        this.iron = iron;
    }

    public float getCholestrol() {
        return cholestrol;
    }

    public void setCholestrol(float cholestrol) {
        this.cholestrol = cholestrol;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    public boolean isEatable() {
        return eatable;
    }

    public void setEatable(boolean eatable) {
        this.eatable = eatable;
    }

    public String getRichIn() {
        return richIn;
    }

    public void setRichIn(String richIn) {
        this.richIn = richIn;
    }

    public String getPrefRoutine() {
        return prefRoutine;
    }

    public void setPrefRoutine(String prefRoutine) {
        this.prefRoutine = prefRoutine;
    }

    public float getVitaminD() {
        return vitaminD;
    }

    public void setVitaminD(float vitaminD) {
        this.vitaminD = vitaminD;
    }

    public float getVitaminE() {
        return vitaminE;
    }

    public void setVitaminE(float vitaminE) {
        this.vitaminE = vitaminE;
    }

    public float getVitaminK() {
        return vitaminK;
    }

    public void setVitaminK(float vitaminK) {
        this.vitaminK = vitaminK;
    }

    public float getMagnissium() {
        return magnissium;
    }

    public void setMagnissium(float magnissium) {
        this.magnissium = magnissium;
    }

    public float getZinc() {
        return zinc;
    }

    public void setZinc(float zinc) {
        this.zinc = zinc;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
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
