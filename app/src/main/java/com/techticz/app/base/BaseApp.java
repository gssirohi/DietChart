package com.techticz.app.base;

import android.app.Application;

import com.techticz.app.utility.CommonUtils;


/**
 * Created by gssirohi on 3/7/16.
 */
public class BaseApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        CommonUtils.increaseLaunchCount(this);
        AppCore.initialize(this);
    }

}
