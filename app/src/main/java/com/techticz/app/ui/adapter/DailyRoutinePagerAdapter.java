package com.techticz.app.ui.adapter;

/**
 * Created by gssirohi on 18/7/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.techticz.app.ui.fragment.DayMealsListFragment;

import java.util.Calendar;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DailyRoutinePagerAdapter extends FragmentPagerAdapter {

    private final DayMealsListFragment.OnListFragmentInteractionListener listner;
    private DayMealsListFragment weeklyFragments[] = new DayMealsListFragment[7];

    public DailyRoutinePagerAdapter(FragmentManager fm, DayMealsListFragment.OnListFragmentInteractionListener listner) {
        super(fm);
        this.listner = listner;
    }

    public DayMealsListFragment getDayFragment(int calenderDay) {
        int dayIndex = 0;
        switch (calenderDay) {
            case Calendar.MONDAY:
                dayIndex = 0;
                break;
            case Calendar.TUESDAY:
                dayIndex = 1;
                break;
            case Calendar.WEDNESDAY:
                dayIndex = 2;
                break;
            case Calendar.THURSDAY:
                dayIndex = 3;
                break;
            case Calendar.FRIDAY:
                dayIndex = 4;
                break;
            case Calendar.SATURDAY:
                dayIndex = 5;
                break;
            case Calendar.SUNDAY:
                dayIndex = 6;
                break;
        }
        if (weeklyFragments != null && weeklyFragments.length >= dayIndex)
            return weeklyFragments[dayIndex];
        else
            return null;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (weeklyFragments[position] == null) {
            weeklyFragments[position] = DayMealsListFragment.newInstance(position);
            weeklyFragments[position].setItemClickListner(listner);
        }
        return weeklyFragments[position];
    }

    @Override
    public int getCount() {
        // Show 7 total pages.
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "MONDAY";
            case 1:
                return "TUESDAY";
            case 2:
                return "WEDNESDAY";
            case 3:
                return "THURSDAY";
            case 4:
                return "FRIDAY";
            case 5:
                return "SATURDAY";
            case 6:
                return "SUNDAY";
        }
        return "OOPS!";
    }
}
