package com.techticz.app.domain.repository.database;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import com.techticz.app.domain.model.pojo.AddedFood;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.Food;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gssirohi on 9/7/17.
 */

public class Converters {
    @TypeConverter
    public static String dayMealToString(DayMeals dayMeal) {
        String str = dayMeal.getR1() + ":"
                + dayMeal.getR2() + ":"
                + dayMeal.getR3() + ":"
                + dayMeal.getR4() + ":"
                + dayMeal.getR5() + ":"
                + dayMeal.getR6() + ":"
                + dayMeal.getR7();
        return str;
    }

    @TypeConverter
    public static DayMeals stringToDayMeals(String str) {

        String[] ids = str.split(":");
        DayMeals dayMeals = new DayMeals();
        dayMeals.setR1(Long.parseLong(ids[0]));
        dayMeals.setR2(Long.parseLong(ids[1]));
        dayMeals.setR3(Long.parseLong(ids[2]));
        dayMeals.setR4(Long.parseLong(ids[3]));
        dayMeals.setR5(Long.parseLong(ids[4]));
        dayMeals.setR6(Long.parseLong(ids[5]));
        dayMeals.setR7(Long.parseLong(ids[6]));

        return dayMeals;
    }


    @TypeConverter
    public static String listToString(List<Integer> list) {
        String str = "";
        if(list == null || list.isEmpty()) return str;
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            Integer num = it.next();
            if (num != null) {
                str = str + num;
            }
            if (it.hasNext()) {
                str = str + ":";
            }
        }
        return str;
    }

    @TypeConverter
    public static List<Integer> stringtoList(String str) {
        List<Integer> list = new ArrayList<>();
        if(TextUtils.isEmpty(str)) return list;
        String[] ids = str.split(":");
        for (String num : ids) {
            try {
                list.add(Integer.parseInt(num));
            } catch (Exception e) {

            }
        }
        return list;
    }


    @TypeConverter
    public static String longListToString(List<Long> list) {
        String str = "";
        if(list == null) return str;
        Iterator<Long> it = list.iterator();
        while (it.hasNext()) {
            Long num = it.next();
            if (num != null) {
                str = str + num;
            }
            if (it.hasNext()) {
                str = str + ":";
            }
        }
        return str;
    }

    @TypeConverter
    public static List<Long> stringtoLongList(String str) {
        List<Long> list = new ArrayList<>();
        if(TextUtils.isEmpty(str)) return list;

        String[] ids = str.split(":");

        for (String num : ids) {
            try {
                list.add(Long.parseLong(num));
            } catch (Exception e) {

            }
        }
        return list;
    }

    @TypeConverter
    public static String addedFoodListToString(List<AddedFood> addedFoods){
        String str = "";
        Iterator<AddedFood> it = addedFoods.iterator();

        while (it.hasNext()){
            AddedFood af = it.next();
            if(af != null){
                Long id  = af.getFoodId();
                int s = af.getServing();
                String conversion = id+"@"+s;
                str = str+conversion;
                if(it.hasNext()){
                    str = str+":";
                }
            }
        }
        return str;
    }

    @TypeConverter
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

    public static String arrayToString(int[] ints) {
        String s = "";
        for(int i = 0;i<ints.length;i++){
            s=s+ints[i];
            if(i<ints.length-1){
                s= s+":";
            }
        }
        return s;
    }

    public static int[] stringToArray(String s){
        if(TextUtils.isEmpty(s)) return new int[]{};
        String[] ss = s.split(":");
        int[] ints = new int[ss.length];
        int i = 0;
        for(String str: ss){
            ints[i++] = Integer.parseInt(str);
        }
        return ints;
    }
}
