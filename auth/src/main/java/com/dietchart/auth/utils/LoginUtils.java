package com.dietchart.auth.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.dietchart.auth.LoginActivity;
import com.dietchart.auth.constant.AuthProviders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 3/9/17.
 */

public class LoginUtils {



    public static void performAppLogin(Context context, FirebaseUser user, AuthProviders provider) {
        SharedPreferences pref = context.getSharedPreferences("app_login", MODE_PRIVATE);
        SharedPreferences.Editor et = pref.edit();
        et.putString("fire_base_user_id",user.getUid());
        et.putInt("provider",provider.getCode());
        et.commit();
    }

    public static AuthProviders getCurrentAuthProvider(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences("app_login", MODE_PRIVATE);
        int code = pref.getInt("provider",0);
        return AuthProviders.getbyCode(code);
    }

    public static String getFirbaseUserId(Context ctx){
        SharedPreferences pref = ctx.getSharedPreferences("app_login", MODE_PRIVATE);
        return pref.getString("fire_base_user_id",null);
    }

    public static void performAppLogout(Context context) {
        SharedPreferences pref = context.getSharedPreferences("app_login", MODE_PRIVATE);
        SharedPreferences.Editor et = pref.edit();
        et.putString("fire_base_user_id","");
        et.putInt("provider",0);
        et.commit();
    }

    public static boolean matchUser(String emailOrPhone) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(!TextUtils.isEmpty(user.getEmail())) {
            if (emailOrPhone.equalsIgnoreCase(user.getEmail())){
                return true;
            }
        } else if(!TextUtils.isEmpty(user.getPhoneNumber())){
            if (emailOrPhone.equalsIgnoreCase(user.getPhoneNumber())){
                return true;
            }
        }
        return false;
    }

    public static String getUserCredential() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return null;
        if(!TextUtils.isEmpty(user.getEmail())) {
            return user.getEmail();
        } else if(!TextUtils.isEmpty(user.getPhoneNumber())){
            return user.getPhoneNumber();
        }
        return null;
    }
}
