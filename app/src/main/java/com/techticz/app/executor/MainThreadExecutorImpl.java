package com.techticz.app.executor;


import android.os.Handler;
import android.os.Looper;

/**
 * Main Thread executor using classic Looper.getMainLooper() from Android
 */
public class MainThreadExecutorImpl implements IMainThreadExecutor {

    private Handler handler;

    public MainThreadExecutorImpl() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
