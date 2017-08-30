package com.techticz.app.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.techticz.app.constant.InteractorExecutors;
import com.techticz.app.constant.Repositories;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.CheckSystemHealthInteractor;
import com.techticz.app.domain.interactor.CreateFoodInteractor;
import com.techticz.app.domain.interactor.CreateMealInteractor;
import com.techticz.app.domain.interactor.CreateMealPlanInteractor;
import com.techticz.app.domain.interactor.FetchAllMealsInteractor;
import com.techticz.app.domain.interactor.FetchBlobInteractor;
import com.techticz.app.domain.interactor.FetchFoodListInteractor;
import com.techticz.app.domain.interactor.FetchImageInteractor;
import com.techticz.app.domain.interactor.FetchMealDetailsInteractor;
import com.techticz.app.domain.interactor.FetchMealListInteractor;
import com.techticz.app.domain.interactor.FetchMealPlanListInteractor;
import com.techticz.app.domain.interactor.FetchProductInteractor;
import com.techticz.app.domain.interactor.FetchDayMealListInteractor;
import com.techticz.app.domain.interactor.GetMealPlanInteractor;
import com.techticz.app.domain.interactor.UseCase;
import com.techticz.app.domain.repository.IAppRepository;
import com.techticz.app.domain.repository.api.APIRepository;
import com.techticz.app.domain.repository.cache.CacheRepository;
import com.techticz.app.domain.repository.database.DataBaseRepository;
import com.techticz.app.domain.repository.mock.MockRepository;
import com.techticz.app.executor.AsyncLoaderInteractorExecutor;
import com.techticz.app.executor.IInteractorExecutor;
import com.techticz.app.executor.IMainThreadExecutor;
import com.techticz.app.executor.InteractorAsyncLoader;
import com.techticz.app.executor.MainThreadExecutorImpl;
import com.techticz.app.executor.ThreadInteractorExecutor;

/**
 * Created by gssirohi on 25/8/16.
 */
public class Provider {

    private final Configuration config;
    private final MainThreadExecutorImpl mainThreadExecutor;
    private final ThreadInteractorExecutor threadInteractorExecutor;
    private final MockRepository mockRepository;
    private final APIRepository apiRepository;
    private final DataBaseRepository databaseRepository;
    private final CacheRepository cacheRepository;

    public Provider(Context context) {
        this.config = new Configuration(context);
        this.mainThreadExecutor = new MainThreadExecutorImpl();
        this.threadInteractorExecutor = new ThreadInteractorExecutor();
        this.mockRepository = new MockRepository();
        this.apiRepository = new APIRepository();
        this.cacheRepository = new CacheRepository();
        this.databaseRepository = new DataBaseRepository(context);
    }

    public Configuration getConfig() {
        return config;
    }

    public UseCase getUseCaseImpl(Context context, UseCases type) {
        UseCase useCase = null;
        switch (type) {
            case FETCH_BLOB:
                return new FetchBlobInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case CHECK_SYSTEM:
                return new CheckSystemHealthInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case FETCH_PLAN_LIST:
                return new FetchMealPlanListInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case CREATE_FOOD:
                return new CreateFoodInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case FETCH_FOOD_LIST:
                return new FetchFoodListInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case CREATE_MEAL_PLAN:
                return new CreateMealPlanInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case FETCH_MEAL_PLAN:
                return new GetMealPlanInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case FETCH_DAY_MEALS:
                return new FetchDayMealListInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case FETCH_MEAL_LIST:
                return new FetchMealListInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case FETCH_MEAL_DETAILS:
                return new FetchMealDetailsInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case CREATE_AND_ADD_MEAL:
                return new CreateMealInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case FETCH_PRODUCT_DETAILS:
                return new FetchProductInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
            case FETCH_PRODUCT_IMAGE:
                return new FetchImageInteractor(context, getInteractorExecutor(context, InteractorExecutors.THREAD)
                        , getMainThreadExecutor(), getAppRepository(Repositories.API));
        }
        return useCase;
    }

    public IAppRepository getAppRepository(Repositories type) {
        switch (type) {
            case MOCK:
                return this.mockRepository;
            case API:
                return this.apiRepository;
            case DATABASE:
                return this.databaseRepository;
            case CACHE:
                return this.cacheRepository;
        }
        return null;
    }

    private IMainThreadExecutor getMainThreadExecutor() {
        return mainThreadExecutor;
    }

    private IInteractorExecutor getInteractorExecutor(Context context, InteractorExecutors type) {
        switch (type) {
            case ASYNC_TASK:
                return new AsyncLoaderInteractorExecutor((AppCompatActivity) context, new InteractorAsyncLoader(context));
            case THREAD:
                return this.threadInteractorExecutor;

        }
        return null;
    }

}
