package com.techticz.dietchart.backend.entities;

import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.techticz.dietchart.backend.endpoints.FoodEntityEndpoint;
import com.techticz.dietchart.backend.model.AddedFood;
import com.techticz.dietchart.backend.utils.Converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 26/8/17.
 */

@Entity
public class MealEntity extends Model{

    private String addedFoods;


    @Index
    boolean R1,R2,R3,R4,R5,R6,R7;

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

    public String getAddedFoods() {
        return addedFoods;
    }

    public void setAddedFoods(String addedFoods) {
        this.addedFoods = addedFoods;
    }

    public NutitionInfo extractNutritient() {
        FoodEntityEndpoint endpoint = new FoodEntityEndpoint();
        List<AddedFood> afs = Converters.stringToAddedFoodList(getAddedFoods());
        Long [] ids = Converters.foodIdsFromAddedFood(afs);
        CollectionResponse<FoodEntity> response = endpoint.listForIds(ids, null, null);
        Collection<FoodEntity> foodEntities = response.getItems();
        Iterator<FoodEntity> it = foodEntities.iterator();
        NutitionInfo tnf = new NutitionInfo();
        while (it.hasNext()){
            FoodEntity f = it.next();
            int serving = getServing(f,afs);
            NutitionInfo nf = f.extractNutritions();
            nf.applyServing(serving);
            tnf.add(nf);
        }
        return tnf;
    }

    private int getServing(FoodEntity f, List<AddedFood> afs) {
        for(AddedFood af: afs){
            if(f.getUid().longValue() == af.getFoodId().longValue()){
                return af.getServing();
            }
        }
        return 0;
    }
}
