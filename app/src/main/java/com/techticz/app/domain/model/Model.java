package com.techticz.app.domain.model;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import com.techticz.app.domain.model.pojo.NutitionInfo;

import org.parceler.Parcel;

/**
 * Created by gssirohi on 25/8/16.
 */

@Parcel
public class Model {

    @PrimaryKey(autoGenerate = true)
    Long uid;
    String name;
    String desc;

    boolean recommended,verified,published;

    String verifiedBy;
    String creator;

    @Ignore
    Bitmap bitmap;
    String blobServingUrl;
    String blobKey;

    float calory,carbs,fat,fiber,protine;

    float vitaminA,vitaminB,vitaminC,vitaminD,vitaminE,vitaminK;

    float potassium,calcium,sodium,iron,magnissium,zinc;

    float cholestrol,sugar;

    public NutitionInfo extractNutritions() {
        NutitionInfo nf = new NutitionInfo();
        nf.setCalory(calory);
        nf.setCarbs(carbs);
        nf.setFat(fat);
        nf.setProtine(protine);
        nf.setFiber(fiber);

        nf.setVitaminA(vitaminA);
        nf.setVitaminB(vitaminB);
        nf.setVitaminC(vitaminC);
        nf.setVitaminD(vitaminD);
        nf.setVitaminE(vitaminE);
        nf.setVitaminK(vitaminK);

        nf.setCalcium(calcium);
        nf.setSodium(sodium);
        nf.setPotassium(potassium);
        nf.setMagnissium(magnissium);
        nf.setZinc(zinc);
        nf.setIron(iron);

        nf.setSugar(sugar);
        nf.setCholestrol(cholestrol);

        return nf;
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

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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

    public void setNutritionInfo(NutitionInfo info) {
        calory = info.getCalory();
        carbs = info.getCarbs();
        fat = info.getFat();
        protine = info.getProtine();
        fiber = info.getFiber();

        vitaminA = info.getVitaminA();
        vitaminB = info.getVitaminB();
        vitaminC  = info.getVitaminC();
        vitaminD = info.getVitaminD();
        vitaminE = info.getVitaminE();
        vitaminK = info.getVitaminK();

        calcium = info.getCalcium();
        potassium = info.getPotassium();
        magnissium = info.getMagnissium();
        sodium = info.getSodium();
        zinc = info.getZinc();
        iron = info.getIron();

        sugar = info.getSugar();
        cholestrol = info.getCholestrol();
    }
}
