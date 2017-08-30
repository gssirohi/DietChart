package com.techticz.app.ui.adapter;

/**
 * Created by gssirohi on 18/7/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.ui.activity.BrowseMealPlanActivity;
import com.techticz.app.ui.fragment.DayMealsListFragment;

import java.util.Calendar;
import java.util.List;


public class MealPlanPagerAdapter extends RecyclerView.Adapter<MealPlanPagerAdapter.ViewHolder> {

    private final CallBack callBack;
    private List<MealPlan> data;

    public MealPlanPagerAdapter(List<MealPlan> data, CallBack callBack) {
        this.data = data;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.meal_plan_list_item_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.planImage.setImageResource(R.drawable.bg_card_3);
        holder.planName.setText(data.get(position).getName());
        holder.planDesc.setText(data.get(position).getDesc());
        holder.planCalory.setText(""+data.get(position).getDailyCalory());
        holder.planImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onMealPlanItemClicked(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView planImage;
        private TextView planName;
        private TextView planDesc;
        private TextView planCalory;

        public ViewHolder(View itemView) {
            super(itemView);
            planImage = (ImageView) itemView.findViewById(R.id.plan_image);
            planName = (TextView)itemView.findViewById(R.id.plan_name);
            planDesc = (TextView)itemView.findViewById(R.id.plan_desc);
            planCalory = (TextView)itemView.findViewById(R.id.plan_calory);
        }
    }


    public interface CallBack{
        void onMealPlanItemClicked(MealPlan plan);
    }

}
