package com.techticz.app.base;

import android.content.Context;

import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.MainThreadExecutorImpl;
import com.techticz.app.executor.ThreadInteractorExecutor;
import com.techticz.app.utility.AppLogger;


/**
 * Created by gssirohi on 3/7/16.
 */
public class AppCore {
    private static AppCore ourInstance;
    private final Context context;
    private final Provider provider;
    private boolean debugMode;
    private Configuration config;
    public IAppRepository appRepository;
    public MainThreadExecutorImpl mainThreadExecutor;
    public ThreadInteractorExecutor interactorExecutor;

    private AppCore(Context context) {
        this.context = context;
        this.provider = new Provider(context);
    }

    public static AppCore getInstance() {
        if (ourInstance != null) {
            return ourInstance;
        } else {
            AppLogger.i(AppCore.class, "App Core is not initialized");
            return null;
        }
    }

    public static AppCore getInstance(Context context) {
        if (ourInstance == null) {
            return new AppCore(context);
        }
        return ourInstance;
    }

    public static void initialize(Context context) {
        ourInstance = new AppCore(context);
        // objectGraph = ObjectGraph.create(new RootModule(context));
        // objectGraph.inject(context);
    }

    public Provider getProvider() {
        return provider;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isDebugMode() {
        return debugMode;
    }


}
