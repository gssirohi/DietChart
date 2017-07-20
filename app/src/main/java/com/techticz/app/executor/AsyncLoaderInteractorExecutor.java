package com.techticz.app.executor;


import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gssirohi on 4/9/16.
 */
public class AsyncLoaderInteractorExecutor implements IInteractorExecutor, LoaderManager.LoaderCallbacks<Object> {
    private final AppCompatActivity activity;
    private final InteractorAsyncLoader loader;
    private boolean running;

    public AsyncLoaderInteractorExecutor(AppCompatActivity activity, InteractorAsyncLoader loader) {
        this.activity = activity;
        this.loader = loader;
    }

    @Override
    public void performAction(IInteractor interactor) {
        this.loader.setInteractor(interactor);
        activity.getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void cancelCurrentRequest() {
        boolean cancelled = loader.cancelLoad();
        setRunning(!cancelled);
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        setRunning(true);
        return this.loader;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

        setRunning(false);
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
