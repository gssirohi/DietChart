package com.techticz.app.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.techticz.app.R;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.Key;
import com.techticz.app.domain.model.pojo.Meal;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.ui.adapter.DailyRoutinePagerAdapter;
import com.techticz.app.ui.viewmodel.contract.IMealRoutineViewModel;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link DayMealsListFragment.OnListFragmentInteractionListener}
 * interface.
 */
public class DailyRoutineTabFragment extends Fragment implements DayMealsListFragment.OnListFragmentInteractionListener {

    private DailyRoutinePagerAdapter mDailyRoutinePagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private DayMealsListFragment.OnListFragmentInteractionListener mListener;
    private int activeDay;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DailyRoutineTabFragment() {
    }

    public static DailyRoutineTabFragment newInstance() {
        DailyRoutineTabFragment fragment = new DailyRoutineTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        initializeTripList(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Key.ADD_MEAL && resultCode == Activity.RESULT_OK) {
            int day = data.getIntExtra("day", 0);
                     /*if(day == tabPosition){
                         isForceLoad = true;
                         loadRoutinesForDay();
                     }*/
        }
    }

    public void initializeTripList(View view) {

/*
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
*/

        initializePagerAdapter(view);

    }


    public void initializePagerAdapter(View view) {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        if (mDailyRoutinePagerAdapter == null)
            mDailyRoutinePagerAdapter = new DailyRoutinePagerAdapter(getActivity().getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        if (mViewPager == null)
            mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mDailyRoutinePagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                activeDay = position;
                ((DayMealsListFragment) mDailyRoutinePagerAdapter.getItem(position)).loadRoutinesForDay();//load trips
            }
        });
        mViewPager.setOffscreenPageLimit(0);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onLoadMoreDataRequest() {
        //   iMealsListPresenter.onFetchRoutinesByDay(false);
    }

    @Override
    public void onListFragmentItemInteraction(MealRoutine item) {
        //getNavigator().navigateToCharacterDetailsScreen(item);
        Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
        // Create new fragment and transaction
        Meal character = (Meal) item.getMeal();
        // ProductDetailsFragment newFragment = ProductDetailsFragment.newInstance(character.getResourceURI());
        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed

        //TODO: put tablet and mobile logic here
        // transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack("label");

// Commit the transaction
        transaction.commit();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof DayMealsListFragment.OnListFragmentInteractionListener) {
//            mListener = (DayMealsListFragment.OnListFragmentInteractionListener) context;
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


    public int getActiveSection() {
        return activeDay;
    }

    public void loadRoutinesOfTheDay(int day) {
        ((DayMealsListFragment) mDailyRoutinePagerAdapter.getItem(day)).forceLoadRoutinesForDay();
    }

    public MealRoutine getNextMealRoutine(){
        return ((DayMealsListFragment) mDailyRoutinePagerAdapter.getItem(activeDay)).getNextMealRoutine();
    }
}
