package com.techticz.app.domain.model.pojo;

/**
 * Created by gssirohi on 1/7/17.
 */

public class HealthProfile {
    private String dob;
    private String gender;
    private String foodPref;
    private CharSequence[] nonVegPref;
    private String activityLevel;
    private float height;
    private float weight = 62;
    private CharSequence[] prescribedNutri;

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setFoodPref(String foodPref) {
        this.foodPref = foodPref;
    }

    public String getFoodPref() {
        return foodPref;
    }

    public void setNonVegPref(CharSequence[] nonVegPref) {
        this.nonVegPref = nonVegPref;
    }

    public CharSequence[] getNonVegPref() {
        return nonVegPref;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public void setPrescribedNutri(CharSequence[] prescribedNutri) {
        this.prescribedNutri = prescribedNutri;
    }

    public CharSequence[] getPrescribedNutri() {
        return prescribedNutri;
    }
}
