
package com.techticz.app.domain.model.pojo;

import android.arch.persistence.room.Entity;

import com.techticz.app.domain.model.Model;


import org.parceler.Parcel;

import java.util.ArrayList;

import java.util.List;

@Entity(tableName = Meal.TableName)
@Parcel
public class Meal extends Model {

    public final static String TableName = "meals";
    boolean R1,R2,R3,R4,R5,R6,R7;


    public Meal() {
    }

     List<AddedFood> addedFoods;

     List<Long> foodIds;

    public List<Integer> getPrefRoutine() {
        List<Integer> list = new ArrayList<>();
        if(isR1())list.add(1);
        if(isR2())list.add(2);
        if(isR3())list.add(3);
        if(isR4())list.add(4);
        if(isR5())list.add(5);
        if(isR6())list.add(6);
        if(isR7())list.add(7);
        return list;
    }

    public void setPrefRoutine(List<Integer> prefRoutine) {
        if(prefRoutine != null){
            for(Integer i: prefRoutine){
                switch(i){
                    case 1:setR1(true);break;
                    case 2:setR2(true);break;
                    case 3:setR3(true);break;
                    case 4:setR4(true);break;
                    case 5:setR5(true);break;
                    case 6:setR6(true);break;
                    case 7:setR7(true);break;
                }
            }
        }
    }

    public List<Long> getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(List<Long> foodIds) {
        this.foodIds = foodIds;
    }

    public List<AddedFood> getAddedFoods() {
        if(addedFoods == null) addedFoods = new ArrayList<>();
        return addedFoods;
    }

    public void setAddedFoods(List<AddedFood> addedFoods) {
        this.addedFoods = addedFoods;
    }


    public boolean isR1() {
        return R1;
    }

    public void setR1(boolean r1) {
        R1 = r1;
    }

    public boolean isR2() {
        return R2;
    }

    public void setR2(boolean r2) {
        R2 = r2;
    }

    public boolean isR3() {
        return R3;
    }

    public void setR3(boolean r3) {
        R3 = r3;
    }

    public boolean isR4() {
        return R4;
    }

    public void setR4(boolean r4) {
        R4 = r4;
    }

    public boolean isR5() {
        return R5;
    }

    public void setR5(boolean r5) {
        R5 = r5;
    }

    public boolean isR6() {
        return R6;
    }

    public void setR6(boolean r6) {
        R6 = r6;
    }

    public boolean isR7() {
        return R7;
    }

    public void setR7(boolean r7) {
        R7 = r7;
    }

    public void updateFoodServing(Long foodId, int serving) {
        List<AddedFood> afs = getAddedFoods();
        for(AddedFood af: afs){
            if(af.getFoodId() == foodId){
                af.setServing(serving);
            }
        }
    }

}
