package com.techticz.app.ui.customview;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.techticz.app.R;
import com.techticz.app.domain.interactor.FetchImageInteractor;
import com.techticz.app.domain.model.pojo.HealthGoalProfile;
import com.techticz.app.domain.model.pojo.HealthProfile;


/**
 * TODO: document your custom view class.
 */

public class HealthGoalCardView extends FrameLayout {
    private final GoalSelectListner listner;
    private HealthGoalProfile viewModel;
    private FetchImageInteractor interactor;
    private LinearLayout goalContent;
    private LinearLayout mainContainer;
    private Switch goalSwitch;
    private TextInputLayout tilTargetWeight;
    private SeekBar seekBarTime;
    private TextView targetTime;
    private TextView targetCal;
    private TextView targetExcercise;
    private TextView goalName;
    private TextView goalDesc;
    private LinearLayout targetTimeContainer;

    public HealthGoalCardView(Context context, HealthGoalProfile goalProfile, GoalSelectListner listner) {
        super(context);
        this.listner = listner;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        viewModel = goalProfile;
        init(context);

    }


    private void init(Context context) {
        ViewGroup itemView =
                (ViewGroup) LayoutInflater.from(context)
                        .inflate(R.layout.health_goal_card_layout, null, false);
        addView(itemView);
        mainContainer = (LinearLayout) findViewById(R.id.ll_main_container);
        goalSwitch = (Switch) findViewById(R.id.switch_goal);

        goalName = (TextView) findViewById(R.id.tv_goal_name);
        goalDesc = (TextView) findViewById(R.id.tv_goal_desc);

        goalContent = (LinearLayout) findViewById(R.id.ll_health_goal_card_content);
        tilTargetWeight = (TextInputLayout) findViewById(R.id.til_target_weight);

        targetTimeContainer = (LinearLayout) findViewById(R.id.ll_target_time);
        targetTime = (TextView) findViewById(R.id.tv_target_time);
        seekBarTime = (SeekBar) findViewById(R.id.skbr_target_time);

        targetCal = (TextView) findViewById(R.id.tv_target_cal);
        targetExcercise = (TextView) findViewById(R.id.tv_excercise);

        tilTargetWeight.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                handleTargetWeightChange(editable.toString());
            }
        });
        seekBarTime.setMax(96);
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                handleSeekBarProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        goalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    goalContent.setVisibility(VISIBLE);
                    //     listner.onGoalSelected(viewModel.getGoalType());
                } else {
                    goalContent.setVisibility(GONE);
                }
            }
        });

        fillDetails(viewModel);
    }

    private void handleSeekBarProgress(int week) {
        viewModel.setTargetWeeks(week);
        seekBarTime.setProgress(viewModel.getTargetWeeks());
        targetTime.setText(viewModel.getTargetWeeks() + " week(s)");

        int cal = calculateTargetCalory();
        targetCal.setText(cal + " calories");

        targetExcercise.setText(calculateExcercises());
    }

    private void handleTargetWeightChange(String weight) {
        if (TextUtils.isEmpty(weight)) {
            weight = "0";
        }
        viewModel.setTargetWeight(Integer.parseInt(weight));
        int cal = calculateTargetCalory();
        targetCal.setText(cal + " calories");

        targetExcercise.setText(calculateExcercises());
    }

    public void fillDetails(HealthGoalProfile viewModel) {
        this.viewModel = viewModel;
        //     goalName.setText(viewModel.getName());
        //   goalDesc.setText(viewModel.getDesc());
        // mainContainer.setBackgroundResource(viewModel.getImageUrl());

//        if(viewModel.getGoalType().equalsIgnoreCase("maintain")){
//            targetTimeContainer.setVisibility(GONE);
//        }
        if (!viewModel.isActive()) {
            goalContent.setVisibility(View.GONE);
            goalSwitch.setChecked(false);
        } else {
            goalSwitch.setChecked(true);
            goalContent.setVisibility(View.VISIBLE);

            tilTargetWeight.getEditText().setText(viewModel.getTargetWeight());
            seekBarTime.setProgress(viewModel.getTargetWeeks());
            targetTime.setText(viewModel.getTargetWeeks() + " week(s)");

            int cal = calculateTargetCalory();
            targetCal.setText(cal + " calories");

            targetExcercise.setText(calculateExcercises());
        }

    }

    private String calculateExcercises() {
        return "PullUps, PushUps, Weight Lifting";
    }

    private int calculateTargetCalory() {
        HealthProfile profile = new HealthProfile();
        float gain = viewModel.getTargetWeight() - profile.getWeight();
        int weeks = seekBarTime.getProgress();

        float maintain = profile.getWeight() * 35;
        float weightGainPerweek = gain / weeks;

        float extraCalsPerDayForOneWeek = weightGainPerweek * 30 * 2;

        return (int) maintain + (int) extraCalsPerDayForOneWeek;
    }

    public void selectGoal(boolean select) {
        if (select) {
            goalSwitch.setChecked(true);
        } else {
            goalSwitch.setChecked(false);
        }
    }

    public interface GoalSelectListner {
        public void onGoalSelected(String goalType);
    }

}
