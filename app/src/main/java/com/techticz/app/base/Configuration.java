package com.techticz.app.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gssirohi on 5/7/16.
 */
public class Configuration {
    private static final String CONFIG_ACCESSIBLE_PRODUCTS = "Configuration.Prefs.ACCESSIBLE_PRODUCTS";
    private final Context mContext;
    private final String CONFIG_PREFS = "com.techticz.app.base.Configuration.Prefs";

    public Configuration(Context context) {
        mContext = context;
    }

    private SharedPreferences getConfigurationPrefs() {
        SharedPreferences prefs = mContext.getSharedPreferences(CONFIG_PREFS, Context.MODE_PRIVATE);
        return prefs;
    }

}
