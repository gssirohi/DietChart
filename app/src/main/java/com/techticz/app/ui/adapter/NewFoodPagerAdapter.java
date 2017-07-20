package com.techticz.app.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techticz.app.R;

/**
 * Created by gssirohi on 12/7/17.
 */

public class NewFoodPagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final OnPageCreatedListner listner;
    private ViewGroup basicView;
    private ViewGroup servingView;
    private ViewGroup nutriView;

    public NewFoodPagerAdapter(Context context, OnPageCreatedListner listner) {
        mContext = context;
        this.listner = listner;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        int res = 0;
        ViewGroup layout = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (position) {
            case 0:
                res = R.layout.new_food_basic;
                if (basicView == null) {
                    basicView = (ViewGroup) inflater.inflate(res, collection, false);
                    listner.onPageCreated(basicView, position);
                }
                layout = basicView;
                break;
            case 1:
                res = R.layout.new_food_serving;
                if (servingView == null) {
                    servingView = (ViewGroup) inflater.inflate(res, collection, false);
                    listner.onPageCreated(servingView, position);
                }
                layout = servingView;
                break;
            case 2:
                res = R.layout.new_food_nutrients;
                if (nutriView == null) {
                    nutriView = (ViewGroup) inflater.inflate(res, collection, false);
                    listner.onPageCreated(nutriView, position);
                }
                layout = nutriView;
                break;
        }

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Basic";
            case 1:
                return "Serving";
            case 2:
                return "Nutrients";
        }
        return "Food Details";
    }

    public interface OnPageCreatedListner {
        public void onPageCreated(ViewGroup view, int position);
    }


}
