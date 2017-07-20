package com.techticz.app.base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.techticz.app.utility.AppNavigator;

public abstract class BaseActivity extends AppCompatActivity {

    private AppNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = new AppNavigator(this);
    }

    public AppNavigator getNavigator() {
        return navigator;
    }


    public void showSnackBar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }
}
