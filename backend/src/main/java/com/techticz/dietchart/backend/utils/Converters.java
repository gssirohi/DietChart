package com.techticz.dietchart.backend.utils;

import com.techticz.dietchart.backend.model.AddedFood;

import java.util.ArrayList;
import java.util.List;

import ch.boye.httpclientandroidlib.util.TextUtils;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 26/9/17.
 */

public class Converters {
    public static List<AddedFood> stringToAddedFoodList(String str){
        List<AddedFood> addedFoods = new ArrayList<>();
        if(TextUtils.isEmpty(str)) return addedFoods;

        String[] aFoods = str.split(":");
        for (String aF : aFoods) {
            String[] s = aF.split("@");
            Long id = Long.parseLong(s[0]);
            int serving = Integer.parseInt(s[1]);
            AddedFood addedFood = new AddedFood(id,serving);
            addedFoods.add(addedFood);
        }
        return addedFoods;
    }

    public static Long[] foodIdsFromAddedFood(List<AddedFood> afs) {
        if(afs == null) return new Long[0];
        Long[] ids = new Long[afs.size()];
        int i = 0;
        for(AddedFood af: afs){
            ids[i++] = af.getFoodId();
        }
        return ids;
    }
}
