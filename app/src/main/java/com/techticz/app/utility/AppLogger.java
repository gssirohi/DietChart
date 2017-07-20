package com.techticz.app.utility;

import android.util.Log;

import com.techticz.app.constant.AppConstants;

/**
 * Created by gssirohi on 3/7/16.
 */
public class AppLogger {

    private AppLogger() {
        //You shall not pass
    }

    private static String getTag(Object object) {
        return object.getClass().getCanonicalName();
    }

    public static void i(Object obj, String txt) {
        if (AppConstants.DEBUG_MODE)
            Log.i(getTag(obj), txt);
    }

    public static void e(Object obj, String txt) {
        if (AppConstants.DEBUG_MODE)
            Log.e(getTag(obj), txt);
    }

}
