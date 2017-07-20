package com.techticz.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.techticz.app.R;
import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.HealthGoals;
import com.techticz.app.domain.model.pojo.HealthGoalProfile;
import com.techticz.app.ui.customview.HealthGoalCardView;

public class HealthGoalActivity extends BaseActivity implements HealthGoalCardView.GoalSelectListner {

    private HealthGoalCardView gainView;
    private HealthGoalCardView maintainView;
    private HealthGoalCardView lossView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSubmitClick();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUI();
    }

    private void handleSubmitClick() {
        //getNavigator().navigateToFoodChartActivity();
        getNavigator().navigateToCreateMealPlanActivity();
    }

    private void initUI() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_goals_container);
        ll.removeAllViews();
        gainView = new HealthGoalCardView(this, new HealthGoalProfile(HealthGoals.WEIGHT_GAIN), this);
        maintainView = new HealthGoalCardView(this, new HealthGoalProfile(HealthGoals.WEIGHT_MAINTAIN), this);
        lossView = new HealthGoalCardView(this, new HealthGoalProfile(HealthGoals.WEIGHT_LOSS), this);
        ll.addView(gainView);
        ll.addView(maintainView);
        ll.addView(lossView);
    }

    public static Intent getCallingIntent(Context context) {
        Intent i = new Intent(context, HealthGoalActivity.class);
        return i;
    }

    @Override
    public void onGoalSelected(String goalType) {
        if (goalType.equalsIgnoreCase("gain")) {
            maintainView.selectGoal(false);
            lossView.selectGoal(false);
        } else if (goalType.equalsIgnoreCase("loss")) {
            gainView.selectGoal(false);
            maintainView.selectGoal(false);
        } else if (goalType.equalsIgnoreCase("maintain")) {
            gainView.selectGoal(false);
            lossView.selectGoal(false);
        }
    }
}
