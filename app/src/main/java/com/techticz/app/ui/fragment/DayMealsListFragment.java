package com.techticz.app.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techticz.app.R;
import com.techticz.app.constant.Key;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.ui.activity.DailyRoutineActivity;
import com.techticz.app.ui.adapter.RoutinesRecyclerViewAdapter;
import com.techticz.app.ui.adapter.EndlessRecyclerOnScrollListener;

import com.techticz.app.ui.viewmodel.contract.IMealRoutineViewModel;
import com.techticz.app.utility.AppLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DayMealsListFragment extends Fragment implements DailyRoutineActivity.OnResult {

    private OnListFragmentInteractionListener mListener;
    private int tabPosition;
    private LinearLayoutManager mLayoutManager;
    private RoutinesRecyclerViewAdapter mRecyclerAdapter;
    private List<MealRoutine> routines = new ArrayList<>();
    private boolean loadingFirstTime = true;
    private boolean isForceLoad;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DayMealsListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DayMealsListFragment newInstance(int tabPosition) {
        DayMealsListFragment fragment = new DayMealsListFragment();
        Bundle args = new Bundle();
        args.putInt(Key.TAB_POSITION, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            tabPosition = args.getInt(Key.TAB_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_characters_list_layout, container, false);

        initializeMealsList(view);
        if (((DailyRoutineActivity) getActivity()).getCurrentActiveSection() == tabPosition) {
            AppLogger.i(this, "trying to load meals for tab.." + tabPosition);
            loadRoutinesForDay();
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        AppLogger.i(this, "setUserVisibleHint");
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {

            } else {
                AppLogger.i(this, "setUserVisibleHint loadTripsIfRequired");
                loadRoutinesForDay();
            }
        }
    }

    public void initializeMealsList(View view) {
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            this.mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);

            mRecyclerAdapter = new RoutinesRecyclerViewAdapter(tabPosition, getRoutineList(), mListener);
            recyclerView.setAdapter(mRecyclerAdapter);
            recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                   /* Meal character = new Meal();
                    character.setId(1234);
                    character.setName("Dummy Character");
                    mRecyclerAdapter.getValues().add(character);
                    mRecyclerAdapter.notifyDataSetChanged();
                    loadMoreData();*/
                }
            });
        }
    }

    private List<MealRoutine> getRoutineList() {
        return routines;
    }


    public void loadRoutinesForDay() {
        if ((getRoutineList().isEmpty() && loadingFirstTime) || isForceLoad) {
            loadingFirstTime = false;
            isForceLoad = false;
            ((DailyRoutineActivity) getActivity()).fetchRoutinesForDay(tabPosition, this);
        }
    }

    public RoutinesRecyclerViewAdapter getRecyclerAdapter() {
        return mRecyclerAdapter;
    }

    private void loadMoreData() {
        if (mListener != null) {
            mListener.onLoadMoreDataRequest();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setItemClickListner(OnListFragmentInteractionListener listner) {
        mListener = listner;
    }

    @Override
    public void updateRoutineList(int day, List<MealRoutine> routines) {
        if (day != tabPosition) return;
        this.routines.clear();
        this.routines.addAll(routines);
        getRecyclerAdapter().notifyDataSetChanged();
    }
    @Override
    public void updateMealInRoutine(int day,int routineId, Meal meal){
        if (day != tabPosition) return;
        routines.get(routineId-1).setMeal(meal);
        getRecyclerAdapter().notifyDataSetChanged();
    }
    //currently not working
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Key.ADD_MEAL && resultCode == Activity.RESULT_OK) {
            int day = data.getIntExtra("day", 0);
            if (day == tabPosition) {
                forceLoadRoutinesForDay();
            }
        }
    }

    public void forceLoadRoutinesForDay() {
        isForceLoad = true;
        loadRoutinesForDay();
    }

    public MealRoutine getNextMealRoutine() {
        //get next Routine with meal
        for(MealRoutine routine: routines){
            if(routine.getMeal()!= null){
                return routine;
            }
        }
        //if completed
        return null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentItemInteraction(MealRoutine item);

        void onLoadMoreDataRequest();
    }

}
