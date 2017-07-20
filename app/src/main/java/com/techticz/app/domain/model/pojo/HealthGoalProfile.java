package com.techticz.app.domain.model.pojo;

import android.arch.persistence.room.Entity;

import com.techticz.app.constant.HealthGoals;

/**
 * Created by gssirohi on 2/7/17.
 */
@Entity
public class HealthGoalProfile {
    private int id;
    private int targetWeight;
    private int targetWeeks = 1;
    private boolean isActive;

    public HealthGoalProfile(HealthGoals type) {
        this.id = type.code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getTargetWeeks() {
        return targetWeeks;
    }

    public void setTargetWeeks(int targetWeeks) {
        this.targetWeeks = targetWeeks;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
