package com.techticz.app.ui.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.ExtractNutritionInfoInteractor;
import com.techticz.app.domain.interactor.FetchDayMealListInteractor;
import com.techticz.app.domain.interactor.FetchDayMealListUseCase;
import com.techticz.app.domain.interactor.IExtractNutritientUseCase;
import com.techticz.app.domain.model.pojo.DayMeals;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.domain.model.pojo.NutitionInfo;
import com.techticz.app.domain.repository.database.Converters;
import com.techticz.app.ui.activity.DailyRoutineActivity;
import com.techticz.app.ui.adapter.MealRoutinePagerAdapter;
import com.techticz.app.utility.AppLogger;
import com.techticz.app.utility.AppUtils;
import com.tecticz.powerkit.ui.customview.CircleGraphView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment implements FetchDayMealListUseCase.Callback, IExtractNutritientUseCase.Callback, MealRoutinePagerAdapter.CallBack {

    private CircleGraphView cgvCalory;
    private CircleGraphView cgvProtine;
    private CircleGraphView cgvCarbs;
    private CircleGraphView cgvFat;
    private CircleGraphView cgvFiber;
    private FrameLayout fl_next_meal;
    private int lastRoutine = -1;
    private List<MealRoutine> todayRoutines;
    private DiscreteScrollView scroller;
    private MealRoutinePagerAdapter routinesAdapter;
    private NutitionInfo todaysTotalNutri = new NutitionInfo();
    private NutitionInfo todaysEatenNutri = new NutitionInfo();
    private int mToday = 0;
    int[] mStatus = new int[]{0,0,0,0,0,0,0};
    private boolean resetActive;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    public static DashBoardFragment newInstance() {
        DashBoardFragment fragment = new DashBoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dash_board, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        cgvCalory = (CircleGraphView)getView().findViewById(R.id.cgv_calory);
        cgvProtine = (CircleGraphView)getView().findViewById(R.id.cgv_protine);
        cgvCarbs = (CircleGraphView)getView().findViewById(R.id.cgv_carbs);
        cgvFat = (CircleGraphView)getView().findViewById(R.id.cgv_fat);
        fl_next_meal  = (FrameLayout)getView().findViewById(R.id.fl_next_meal_container);
        scroller = (DiscreteScrollView)getView().findViewById(R.id.scroller);
        scroller.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build());
        initData();
        refreshView();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

    }
    private void initData() {
        mToday = AppUtils.getToday();
        queryEatenOrMissedRoutine();
        todayRoutines = new ArrayList<MealRoutine>();
        routinesAdapter = new MealRoutinePagerAdapter(todayRoutines, this);
        scroller.setAdapter(routinesAdapter);

    }
    public void refreshView() {
        if(getActivity() == null) return;
        fetchTodaysTargetNutritions();

    }
    private void fetchTodaysTargetNutritions(){
        IExtractNutritientUseCase usecase = (IExtractNutritientUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(getContext(), UseCases.CALCULATE_NUTRI);
        int today = mToday;
        AppLogger.i(this,"fetching day meals for day:"+today);
        DayMeals dayMeals = ((DailyRoutineActivity)getContext()).getDayMealIdsByDay(today);
        AppLogger.i(this,"DayMeals is: "+dayMeals.toString());
        usecase.execute(new IExtractNutritientUseCase.Callback() {
            @Override
            public void onError(AppErrors error) {

            }

            @Override
            public void onNutritionFetched(NutitionInfo meal, NutitionInfo dayMeals, NutitionInfo plan) {
                AppLogger.i(this,"Today's Nutri Fetched:"+ dayMeals.toString());
                todaysTotalNutri.add(dayMeals);
                startNutriInfoLoading();
                fetchNextRoutine(mToday);
            }
        }, false, null, dayMeals, null);
    }

    private void loadNextMealFetched() {
        //MealRoutine mealRoutine = ((DailyRoutineActivity)getActivity()).getNextMealRoutine();
        try {
            AppLogger.i(this,"moving to next meal:"+lastRoutine);
            scroller.smoothScrollToPosition(lastRoutine);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void handleMissedIt() {
        registerEatenOrMissedRoutine();
    }

    private void handleAteIt(MealRoutine mr) {
        registerEatenOrMissedRoutine();
        IExtractNutritientUseCase u = (ExtractNutritionInfoInteractor)AppCore.getInstance().getProvider().getUseCaseImpl(getContext(),UseCases.CALCULATE_NUTRI);
        u.execute(this,true,mr.getMeal(),null,null);
    }

    private void handleReset(MealRoutine mr) {
        registerEatenOrMissedRoutine();
        IExtractNutritientUseCase u = (ExtractNutritionInfoInteractor)AppCore.getInstance().getProvider().getUseCaseImpl(getContext(),UseCases.CALCULATE_NUTRI);
        u.execute(this,true,mr.getMeal(),null,null);
    }

    private void registerEatenOrMissedRoutine() {
        SharedPreferences pref = getContext().getSharedPreferences("eaten_day_meals", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = pref.edit();
        e.putString("dayMealsEaten", getRoutineStatusString());
        e.putInt("today",mToday);
        e.commit();

    }

    private void queryEatenOrMissedRoutine() {
        SharedPreferences pref = getContext().getSharedPreferences("eaten_day_meals", Context.MODE_PRIVATE);
        int today = pref.getInt("today",mToday);
        if(today != mToday){
            //day has changed
            registerEatenOrMissedRoutine();
        } else {
            String s = pref.getString("dayMealsEaten","0:0:0:0:0:0:0");
            mStatus = Converters.stringToArray(s);
        }
    }
    private String getRoutineStatusString() {
        for(MealRoutine r:todayRoutines){
            if(r.isEaten()) {
                mStatus[r.getUid()-1] = 1;
            } else if(r.isMissed()){
                mStatus[r.getUid()-1] = 2;
            } else {
                mStatus[r.getUid()-1] = 0;
            }
        }
        String s = Converters.arrayToString(mStatus);
        return s;
    }

    private void startNutriInfoLoading() {
        if(cgvCalory == null) return;
        AppLogger.i(this,"startNutriInfoLoading");
        cgvCalory.start("Calory",todaysTotalNutri.getCalory(),todaysEatenNutri.getCalory(),0);
        cgvProtine.start("Protine",todaysTotalNutri.getProtine(),todaysEatenNutri.getProtine(),0);
        cgvCarbs.start("Carbs",todaysTotalNutri.getCarbs(),todaysEatenNutri.getCarbs(),0);
        cgvFat.start("Fat",todaysTotalNutri.getFat(),todaysEatenNutri.getFat(),0);
    }

    public void reloadNutriInfo(NutitionInfo info){
        cgvCalory.play(info.getCalory());
        cgvProtine.play(info.getProtine());
        cgvCarbs.play(info.getCarbs());
        cgvFat.play(info.getFat());
    }

    public void fetchNextRoutine(int day) {
        if(todayRoutines == null || todayRoutines.isEmpty()) {
            FetchDayMealListInteractor usecase = (FetchDayMealListInteractor) AppCore.getInstance().getProvider().getUseCaseImpl(getContext(), UseCases.FETCH_DAY_MEALS);
            usecase.execute(this, day, ((DailyRoutineActivity) getActivity()).getDayMealIdsByDay(day), false);
        } else {
            int i =-1;
            for(MealRoutine r: todayRoutines) {
                i++;
                if(i <= lastRoutine){
                    continue;
                }
                if(r.getMeal() != null){
                    lastRoutine = i;
                    loadNextMealFetched();
                    return;
                }
            }
        }
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onNutritionFetched(NutitionInfo meal, NutitionInfo dayMeals, NutitionInfo plan) {
        if(meal != null){
            if(resetActive){
                resetActive = false;
                todaysEatenNutri.remove(meal);
            } else {
                todaysEatenNutri.add(meal);
                fetchNextRoutine(mToday);
            }
            reloadNutriInfo(todaysEatenNutri);

        }

    }

    @Override
    public void onRoutineMealList(int day, List<MealRoutine> routines) {
        AppLogger.i(this,"Routines Fetched:"+routines.toString());
        //day should be today and select routine based on time
        ArrayList<MealRoutine> lodedRoutines = new ArrayList<MealRoutine>();
        for(MealRoutine r: routines){
            if(r.getMeal() != null){
                lodedRoutines.add(r);
            }
        }
        syncMealRoutinesWithPreviousStatus(lodedRoutines);
        lastRoutine++;
        todayRoutines.clear();
        todayRoutines.addAll(lodedRoutines);
        routinesAdapter.notifyDataSetChanged();
        loadNextMealFetched();
    }

    private void syncMealRoutinesWithPreviousStatus(ArrayList<MealRoutine> lodedRoutines) {
        for(MealRoutine r: lodedRoutines){
            if(mStatus[r.getUid()-1] == 1){
                r.setEaten(true);
                handleAteIt(r);
            } else if(mStatus[r.getUid()-1] == 2){
                r.setMissed(true);
            } else {

            }
        }
    }

    @Override
    public void onMealRoutineEaten(int index, MealRoutine r) {
        lastRoutine = index;
        handleAteIt(r);
        loadNextMealFetched();
    }

    @Override
    public void onMealRoutineMissed(int index,MealRoutine r) {
        lastRoutine = index;
        handleMissedIt();
        loadNextMealFetched();
    }
    @Override
    public void onMealRoutineReset(int index,MealRoutine r) {
        resetActive = true;
        if(lastRoutine > index) {
            lastRoutine = index;
        }
        handleReset(r);
        loadNextMealFetched();
    }
}

