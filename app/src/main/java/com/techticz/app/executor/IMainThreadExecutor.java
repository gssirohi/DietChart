package com.techticz.app.executor;

/**
 * The interactors callbacks are executed in a different thread, in Android the ui thread to perform
 * view changes
 */
public interface IMainThreadExecutor {

    void execute(Runnable runnable);
}
