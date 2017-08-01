package com.techticz.app.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.provider.Settings;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by gssirohi on 28/8/16.
 */
public class CommonUtils {

    private static final int PRODUCT_IMAGE_WIDTH = 400;

    public static String getDeviceId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static boolean isNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static String convertStreamToString(InputStream inputStream) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));

            String streamLine;
            try {
                while ((streamLine = reader.readLine()) != null) {
                    stringBuilder.append(streamLine + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static boolean isRfsBuild() {
        return true;
    }


    private static int screenWidth = 0;

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static int getLaunchCount(Context context) {
        SharedPreferences pref = context.getSharedPreferences("AppState", Context.MODE_PRIVATE);
        return pref.getInt("launch_count", 1);
    }

    public static void increaseLaunchCount(Context context) {
        SharedPreferences pref = context.getSharedPreferences("AppState", Context.MODE_PRIVATE);
        int count = pref.getInt("launch_count", 1);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("launch_count", count++);
        editor.commit();
        AppLogger.i("Utils", "Launch Count:" + count);
    }

    public static int randomNumber(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static String getDisplayDate(Calendar dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(dob.getTime());
        return date;
    }

    public static byte[] getByteArrayFromBitmap(Bitmap bitmap, boolean scale) {

        Bitmap scaledBitmap = bitmap;
        if (scale) {
            scaledBitmap = scale(bitmap);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    public static  Bitmap scale (Bitmap source){
        int w = source.getWidth();
        double factor = 1.0;
        if (w > PRODUCT_IMAGE_WIDTH) {
            factor = ((double)w) / ((double)PRODUCT_IMAGE_WIDTH);
        }
        else {

        }

        int dw = new Double((((double)factor) * ((double)(source.getWidth())))).intValue();
        int dh = new Double((((double)factor) * ((double)(source.getHeight())))).intValue();
        return Bitmap.createScaledBitmap(source, dw, dh, false);
    }
}
