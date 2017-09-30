package com.techticz.app.ui.adapter;

/**
 * Created by gssirohi on 18/7/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.domain.model.pojo.MealRoutine;
import com.techticz.app.ui.customview.MealRoutineView;

import java.util.List;


import static android.view.View.INVISIBLE;


public class MealRoutinePagerAdapter extends RecyclerView.Adapter<MealRoutinePagerAdapter.ViewHolder> {

    private final CallBack callBack;
    private List<MealRoutine> data;

    public MealRoutinePagerAdapter(List<MealRoutine> data, CallBack callBack) {
        this.data = data;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MealRoutineView view = new MealRoutineView(0,parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.routineView.fillDetails(data.get(position));
        holder.routineView.setStatusActionVisibility(View.VISIBLE);
        final TextView b_ate = (TextView) holder.routineView.findViewById(R.id.ll_status).findViewById(R.id.b_ate_it);
        final TextView b_missed = (TextView)holder.routineView.findViewById(R.id.ll_status).findViewById(R.id.b_missed_it);
        holder.routineView.findViewById(R.id.iv_reset_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.get(position).setEaten(false);
                data.get(position).setMissed(false);
                b_ate.setVisibility(View.VISIBLE);
                b_missed.setVisibility(View.VISIBLE);
                holder.routineView.findViewById(R.id.iv_reset_status).setVisibility(View.INVISIBLE);
                data.get(position).setEaten(false);
                data.get(position).setMissed(false);
                callBack.onMealRoutineReset(position,data.get(position));
            }
        });
        b_ate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b_ate.setVisibility(View.INVISIBLE);
                b_missed.setVisibility(View.INVISIBLE);
                holder.routineView.findViewById(R.id.iv_reset_status).setVisibility(View.VISIBLE);
                handleAteIt(position,data.get(position));
            }
        });
        b_missed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b_ate.setVisibility(View.INVISIBLE);
                b_missed.setVisibility(View.INVISIBLE);
                holder.routineView.findViewById(R.id.iv_reset_status).setVisibility(View.VISIBLE);
                handleMissedIt(position,data.get(position));
            }
        });

        if(data.get(position).isEaten() || data.get(position).isMissed()){
            b_ate.setVisibility(View.INVISIBLE);
            b_missed.setVisibility(View.INVISIBLE);
            holder.routineView.findViewById(R.id.iv_reset_status).setVisibility(View.VISIBLE);
        } else {
            b_ate.setVisibility(View.VISIBLE);
            b_missed.setVisibility(View.VISIBLE);
            holder.routineView.findViewById(R.id.iv_reset_status).setVisibility(View.INVISIBLE);
        }
        holder.routineView.findViewById(R.id.iv_remove).setVisibility(View.INVISIBLE);
    }

    private void handleMissedIt(int index,MealRoutine r) {
        r.setMissed(true);
        callBack.onMealRoutineMissed(index,r);
    }

    private void handleAteIt(int index,MealRoutine mealRoutine) {
        mealRoutine.setEaten(true);
        callBack.onMealRoutineEaten(index,mealRoutine);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private MealRoutineView routineView;

        public ViewHolder(MealRoutineView view) {
            super(view);
            routineView = view;
        }
    }


    public interface CallBack{
        void onMealRoutineEaten(int index,MealRoutine r);
        void onMealRoutineMissed(int index,MealRoutine r);
        void onMealRoutineReset(int index, MealRoutine routine);
    }

}
