package com.tecticz.powerkit.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.tecticz.powerkit.R;


/**
 * Created by gssirohi on 13/7/16.
 */
public class BetterProgressDialog extends Dialog {

    private TextView labelTv;
    private TextView descriptionTv;
    private String label;
    private String description;

    public BetterProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context,android.R.style.Theme_Light);
        this.setCancelable(cancelable);
        this.setOnCancelListener(cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.full_progress_layout);

        labelTv = (TextView)findViewById(R.id.label);
        descriptionTv = (TextView)findViewById(R.id.description);


        ImageView imageView = (ImageView) findViewById(R.id.civ_gif);
        Glide.with(getContext()).load(R.raw.popcorn_transparent).into(imageView);

        refresh();
    }

    public void setLabel(String label) {
        this.label = label;
        refresh();
    }

    private void refresh() {
        if(labelTv != null) {
            labelTv.setText(label);
        }
        if(descriptionTv != null) {
            descriptionTv.setText(description);
        }

    }

    public void setDescription(String desc) {
        this.description = desc;
        refresh();
    }

}
