package com.techticz.app.utility;

import com.techticz.app.constant.AppAPIMethods;
import com.techticz.app.constant.Products;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gssirohi on 10/9/16.
 */
public class AppUtils {

    public static String getProductIdFromUri(String uri) {
        int lastSeparator = uri.lastIndexOf("/");
        int size = uri.length();
        String id = uri.substring(lastSeparator + 1, size);
        AppLogger.i(AppUtils.class, "productUri: " + uri + "    id:" + id);
        return id;
    }

    public static Products getProductTypeFromUri(String uri) {
        if (uri.contains(AppAPIMethods.FETCH_COMICS_LIST)) {
            return Products.COMIC;
        } else if (uri.contains(AppAPIMethods.FETCH_CHARACTERS_LIST)) {
            return Products.CHARACTER;
        } else if (uri.contains(AppAPIMethods.FETCH_EVENTS_LIST)) {
            return Products.EVENT;
        } else if (uri.contains(AppAPIMethods.FETCH_SERIES_LIST)) {
            return Products.SERIES;
        } else if (uri.contains(AppAPIMethods.FETCH_STORIES_LIST)) {
            return Products.STORY;
        } else if (uri.contains(AppAPIMethods.FETCH_CREATORS_LIST)) {
            return Products.CREATOR;
        }
        return null;
    }

    public static String getDayShortName(int day) {
        switch (day) {
            case 0:
                return "mon";
            case 1:
                return "tue";
            case 2:
                return "wed";
            case 3:
                return "thu";
            case 4:
                return "fri";
            case 5:
                return "sat";
            case 6:
                return "sun";
        }
        return "invalid_day";
    }

    public static String getDayFullName(int day) {
        switch (day) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
        }
        return "invalid_day";
    }

    public static String getTimeStamp() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        return timeStamp;
    }

    public static String getBitmapName(MealPlan plan) {
        return "plans/"+plan.getName().trim()+"_"+AppUtils.getTimeStamp();
    }
    public static String getBitmapName(Meal meal) {
        return "meals/"+meal.getName().trim()+"_"+AppUtils.getTimeStamp();
    }
    public static String getBitmapName(Food food) {
        return "foods/"+food.getName().trim()+"_"+AppUtils.getTimeStamp();
    }
}
