package com.techticz.app.executor;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by gssirohi on 4/9/16.
 */
public class InteractorAsyncLoader extends AsyncTaskLoader<Object> {

    private IInteractor interactor;

    public InteractorAsyncLoader(Context context) {
        super(context);
    }

    public void setInteractor(IInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public Object loadInBackground() {
        interactor.run();
        return null;
    }
}
