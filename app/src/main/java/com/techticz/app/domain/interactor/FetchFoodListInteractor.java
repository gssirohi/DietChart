package com.techticz.app.domain.interactor;

import android.content.Context;

import com.techticz.app.domain.exception.AppException;
import com.techticz.app.domain.model.pojo.Food;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.executor.BaseInteractor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.utility.AppLogger;

import java.util.List;

/**
 * Created by gssirohi on 15/7/16.
 */
public class FetchFoodListInteractor extends BaseInteractor implements FetchFoodListUseCase {

    private IAppRepository appRepository;
    private Callback callback;
    private int day;
    private String searchKey;
    private Long[] foodIds;

    public FetchFoodListInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor, IAppRepository appRepository) {
        super(context, interactorExecutor, mainThreadExecutor);
        this.appRepository = appRepository;
    }


    @Override
    public void run() {

        try {
            Thread.sleep(300);

            final List<Food> foods = appRepository.getFoodList(this, searchKey, foodIds);
            dismissDialog();
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFoodListFetched(foods, searchKey);
                    }
                });

        } catch (final AppException e) {
            AppLogger.e(this, "Error on fetch foods");
            if (!isCancelled())
                getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e.getError());
                    }
                });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelCurrentRequest() {
        setCancelled(true);
    }


    @Override
    public void execute(Callback callback, boolean showLoader, String searchKey, Long[] foodIds) {
        this.callback = callback;
        this.searchKey = searchKey;
        this.foodIds = foodIds;
        if (showLoader) showDialog("Searching foods .. ");
        getInteractorExecutor().performAction(this);
    }
}
