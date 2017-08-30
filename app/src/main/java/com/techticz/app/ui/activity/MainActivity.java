package com.techticz.app.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.base.BaseActivity;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.CheckSystemHealthUseCase;
import com.techticz.app.executor.EndPointAsyncTask;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;
import com.techticz.fcm.activity.FCMDemoActivity;

public class MainActivity extends BaseActivity implements CheckSystemHealthUseCase.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.tv_welcome);
        Button b = (Button) findViewById(R.id.b_continue);
        tv.setText("FCM DEMO APP");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleContnueClick();
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction("APPROVE");
        filter.addAction("DECLINE");
// Add other actions as needed

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("APPROVE")) {
                    approve();
                } else if(intent.getAction().equals("DECLINE")) {
                    decline();
                }
            }
        };

        registerReceiver(receiver, filter);
    }

    private void approve() {
        Toast.makeText(this,"Approved",Toast.LENGTH_SHORT).show();
    }

    private void decline() {
        Toast.makeText(this,"Declined",Toast.LENGTH_SHORT).show();
    }

    private void handleContnueClick() {

//        CheckSystemHealthUseCase usecase = (CheckSystemHealthUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.CHECK_SYSTEM);
//        usecase.execute(this,true);
        //startActivity(new Intent(this,FCMDemoActivity.class));
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        long planId = pref.getLong("plan", 0);

        if (planId == 0) {
            getNavigator().navigateToBrowseMealPlanActivity();
        } else {
            getNavigator().navigateToFoodChartActivity(planId);
        }

        //getNavigator().navigateToHealthDetailForm();
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onSystemHealth(SystemHealth health) {
        Toast.makeText(this,health.getDetail(),Toast.LENGTH_SHORT).show();
    }
}
