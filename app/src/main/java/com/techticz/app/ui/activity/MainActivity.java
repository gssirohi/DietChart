package com.techticz.app.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dietchart.auth.utils.LoginUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.techticz.app.R;
import com.techticz.app.base.AppCore;
import com.techticz.app.base.BaseActivity;

import com.techticz.app.constant.AppErrors;
import com.techticz.app.constant.Key;
import com.techticz.app.constant.Repositories;
import com.techticz.app.constant.UseCases;
import com.techticz.app.domain.interactor.CheckSystemHealthUseCase;
import com.techticz.app.domain.interactor.LoginUseCase;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.domain.repository.database.DataBaseRepository;
import com.techticz.app.executor.EndPointAsyncTask;

import com.techticz.dietchart.backend.appUserApi.model.AppUser;
import com.techticz.dietchart.backend.appUserApi.model.MarketResponse;
import com.techticz.dietchart.backend.appUserApi.model.UserLoginResponse;
import com.techticz.dietchart.backend.myApi.model.SystemHealth;
import com.techticz.fcm.activity.FCMDemoActivity;

import java.util.List;

public class MainActivity extends BaseActivity implements CheckSystemHealthUseCase.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.tv_welcome);
        Button b = (Button) findViewById(R.id.b_continue);
        tv.setText("Welcome to Diet Chart");
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

        //handleContnueClick();
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

        //getNavigator().navigateToHealthDetailForm();

        if(TextUtils.isEmpty(LoginUtils.getFirbaseUserId(this)) || FirebaseAuth.getInstance().getCurrentUser() == null) {
            getNavigator().navigateToLoginActivity(this);
        } else {
            //proceedToApp();
            bringMarketPlace();
        }
    }

    @Override
    public void onError(AppErrors error) {

    }

    @Override
    public void onSystemHealth(SystemHealth health) {
        Toast.makeText(this,health.getDetail(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Key.LOGIN && resultCode == RESULT_OK){
            if(!TextUtils.isEmpty(LoginUtils.getFirbaseUserId(this))) {
                getNavigator().navigateToHealthDetailForm();
                bringMarketPlace();
                //proceedToApp();
            }
        }
    }

    private void bringMarketPlace() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AppUser appUser = new AppUser();
        appUser.setName(user.getDisplayName());
        appUser.setUid(user.getUid());
        appUser.setEmail(user.getEmail());
        appUser.setPhone(user.getPhoneNumber());

        LoginUseCase useCase = (LoginUseCase) AppCore.getInstance().getProvider().getUseCaseImpl(this, UseCases.LOGIN);
        useCase.execute(new LoginUseCase.Callback() {
            @Override
            public void onError(AppErrors error) {

            }

            @Override
            public void onLoggedIn(UserLoginResponse loginResponse, List<MealPlan> plans) {
                constructMarket(loginResponse.getMarket());
            }
        },appUser,true);

    }

    private void constructMarket(MarketResponse market) {
        proceedToApp();
    }

    private void proceedToApp() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        long planId = pref.getLong("plan", 0);

        if (planId == 0) {
            getNavigator().navigateToBrowseMealPlanActivity();
        } else {
            getNavigator().navigateToFoodChartActivity(planId);
        }
    }
}
