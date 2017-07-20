package com.techticz.app.ui.adapter;

/**
 * Created by gssirohi on 2/9/16.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends
        RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class
            .getSimpleName();

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 0;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(
            LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (!loading) {
            if (firstVisibleItem + visibleItemCount == totalItemCount) {
                previousTotal = totalItemCount;
                current_page++;
                loading = true;
                onLoadMore(current_page);
            }
        } else if (loading) {
            if (totalItemCount != previousTotal + 1) {// +1 is for considering dummy data added to show progress
                //data loaded
                loading = false;
            }
        }
    }

    public abstract void onLoadMore(int current_page);
}
