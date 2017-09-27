package com.techticz.app.executor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.tecticz.powerkit.ui.dialog.BetterProgressDialog;

public abstract class BaseInteractor implements IInteractor {

    private final BetterProgressDialog betterDialog;
    private Context context;
    private IInteractorExecutor interactorExecutor;
    private IMainThreadExecutor mainThreadExecutor;
    private boolean cancelled;


    private ProgressDialog progressDialog;
    private DialogInterface.OnCancelListener onDialogCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            dialog.dismiss();
        }
    };
    private int dialogType;


    public BaseInteractor(Context context, IInteractorExecutor interactorExecutor, IMainThreadExecutor mainThreadExecutor) {
        this.context = context;
        this.interactorExecutor = interactorExecutor;
        this.mainThreadExecutor = mainThreadExecutor;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(onDialogCancelListener);
        progressDialog.setCancelable(true);

        betterDialog = new BetterProgressDialog(context,true,onDialogCancelListener);

        betterDialog.setCanceledOnTouchOutside(false);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public IInteractorExecutor getInteractorExecutor() {
        return interactorExecutor;
    }

    public void setInteractorExecutor(IInteractorExecutor interactorExecutor) {
        this.interactorExecutor = interactorExecutor;
    }

    public IMainThreadExecutor getMainThreadExecutor() {
        return mainThreadExecutor;
    }

    public void setMainThreadExecutor(IMainThreadExecutor mainThreadExecutor) {
        this.mainThreadExecutor = mainThreadExecutor;
    }


    public void setDialogMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            progressDialog.setMessage(message);
        }
    }


    public void showDialog() {
        showDialog(1);
    }


    public void showDialog(int dialogType){
        this.dialogType = dialogType;
        showDialog(this.dialogType,"Loading ..");
    }

    public void showDialog(String message){
        this.dialogType = 1;
        showDialog(this.dialogType,message);
    }


    public void showDialog(int dialogType,String message){
        this.dialogType = dialogType;
        if(dialogType == 0) {
            if(progressDialog != null && !TextUtils.isEmpty(message)) {
                progressDialog.setMessage(message);
                progressDialog.show();
            }
        } else {
            if (betterDialog != null && !TextUtils.isEmpty(message)) {
                betterDialog.setLabel(message);
                betterDialog.show();
            }
        }
    }

    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (betterDialog != null) {
            betterDialog.dismiss();
        }
    }
}
