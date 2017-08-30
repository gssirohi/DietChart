package com.techticz.app.domain.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 26/8/17.
 */

public class NutitionInfo {
    @SerializedName("calory")
    @Expose
    float calory;

    @SerializedName("carbs")
    @Expose
    float carbs;

    @SerializedName("fat")
    @Expose
    float fat;

    @SerializedName("fiber")
    @Expose
    float fiber;

    @SerializedName("protine")
    @Expose
    float protine;

    @SerializedName("vitaminA")
    @Expose
    float vitaminA;

    @SerializedName("vitaminB")
    @Expose
    float vitaminB;

    @SerializedName("vitaminC")
    @Expose
    float vitaminC;

    @SerializedName("vitamind")
    @Expose
    float vitaminD;

    @SerializedName("vitaminE")
    @Expose
    float vitaminE;

    @SerializedName("vitaminK")
    @Expose
    float vitaminK;

    @SerializedName("potassium")
    @Expose
    float potassium;

    @SerializedName("calcium")
    @Expose
    float calcium;

    @SerializedName("sodium")
    @Expose
    float sodium;

    @SerializedName("iron")
    @Expose
    float iron;

    @SerializedName("magnissium")
    @Expose
    float magnissium;

    @SerializedName("zinc")
    @Expose
    float zinc;

    @SerializedName("cholestrol")
    @Expose
    float cholestrol;

    @SerializedName("sugar")
    @Expose
    float sugar;

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

    public void add(NutitionInfo nf) {
        calory = calory+nf.getCalory();
        carbs = carbs+nf.getCarbs();
        fat = fat+nf.getFat();
        protine = protine+nf.getProtine();
        fiber = fiber+nf.getFiber();

        vitaminA = vitaminA+nf.getVitaminA();
        vitaminB = vitaminB+nf.getVitaminB();
        vitaminC  = vitaminC+nf.getVitaminC();
        vitaminD = vitaminD+nf.getVitaminD();
        vitaminE = vitaminE + nf.getVitaminE();
        vitaminK = vitaminK+ nf.getVitaminK();

        calcium = calcium+nf.getCalcium();
        potassium = potassium+nf.getPotassium();
        magnissium = magnissium+nf.getMagnissium();
        sodium = sodium+nf.getSodium();
        zinc = zinc+nf.getZinc();
        iron = iron+nf.getIron();

        sugar = sugar+nf.getSugar();
        cholestrol = cholestrol+nf.getCholestrol();
    }
}
