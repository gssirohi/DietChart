package com.dietchart.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.dietchart.auth.constant.AuthProviders;
import com.dietchart.auth.fragment.UserDetailsFragment;
import com.dietchart.auth.fragment.LoginFragment;
import com.dietchart.auth.utils.LoginUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,UserDetailsFragment.OnUserFragInteractionListener,LoginFragment.OnLoginFragInteractionListener, FacebookCallback<LoginResult> {

    private static final String TAG = "LoginActivity";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private ProgressDialog mProgressDialog;
    private LoginFragment loginFrag;
    private UserDetailsFragment userFrag;
    private FirebaseUser currentUser;
    private SharedPreferences pref;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        initAuthenticators();
    }

    private void initAuthenticators() {
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mCallbackManager = CallbackManager.Factory.create();
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
    }

    private void initUI() {

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            displayUserDetails(currentUser);
        } else {
            displayLoginScreen();
        }
    }

    private void displayUserDetails(FirebaseUser currentUser) {
        this.currentUser = currentUser;
        if(userFrag == null){
            userFrag = UserDetailsFragment.newInstance();
        }
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //TODO: put tablet and mobile logic here
        transaction.replace(R.id.frag_container, userFrag);
       // transaction.addToBackStack(null);
        transaction.commit();
    }

    private void displayLoginScreen() {
        if(loginFrag == null){
            loginFrag = LoginFragment.newInstance();
        }
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //TODO: put tablet and mobile logic here
        transaction.replace(R.id.frag_container, loginFrag);
       // transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                displayLoginScreen();
                // [END_EXCLUDE]
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            LoginUtils.performAppLogin(LoginActivity.this,user,AuthProviders.GOOGLE);
                            displayUserDetails(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            displayLoginScreen();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }


    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOutWithGoogle() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        displayLoginScreen();
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        displayLoginScreen();
                    }
                });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @Override
    public void onLogoutClick() {
        AuthProviders ap = LoginUtils.getCurrentAuthProvider(this);
        if(ap != null){
            if(ap == AuthProviders.GOOGLE){
                signOutWithGoogle();
            } else if (ap == AuthProviders.FACEBOOK){
                signOutWithFacebook();
            }
        }
        currentUser = null;
        LoginUtils.performAppLogout(this);
        displayLoginScreen();
    }

    @Override
    public void onProceedClick() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onLoginClick(AuthProviders provider) {
        if(provider == AuthProviders.GOOGLE){
            signInWithGoogle();
        }
    }

    @Override
    public void onSubmitOtpClick() {

    }

    @Override
    public void onUserLoggedIn(FirebaseUser user, AuthProviders provider) {

    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public CallbackManager getFBCallBackManager() {
        return mCallbackManager;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.d(TAG, "facebook:onSuccess:" + loginResult);
        handleFacebookAccessToken(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "facebook:onCancel");
        // [START_EXCLUDE]
        Toast.makeText(LoginActivity.this, "Facebook Login canceled.",
                Toast.LENGTH_SHORT).show();
        // [END_EXCLUDE]
    }

    @Override
    public void onError(FacebookException error) {
        Log.d(TAG, "facebook:onError", error);
        // [START_EXCLUDE]
        Toast.makeText(LoginActivity.this, "Facebook Login failed.",
                Toast.LENGTH_SHORT).show();
        // [END_EXCLUDE]
    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            LoginUtils.performAppLogin(LoginActivity.this,user,AuthProviders.FACEBOOK);
                            displayUserDetails(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // showError();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]

    public void signOutWithFacebook() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        LoginUtils.performAppLogout(this);
        displayLoginScreen();
    }

}
