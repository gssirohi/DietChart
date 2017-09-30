package com.techticz.app.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.techticz.app.constant.AppAPIMethods;
import com.techticz.app.constant.Products;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealPlan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    public static int getToday() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);

        //rotate by 1 as monday is our first day and it should be on 0 index
        int dayIndex;
        //1->7 2->1 3->2 4->3 5->4 6->5 7->6
        if(day >= 2){
            dayIndex = day-2;
        } else {
            dayIndex = 6;
        }
        return dayIndex;
    }
}
