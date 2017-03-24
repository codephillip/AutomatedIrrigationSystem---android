package com.codephillip.app.automatedirrigationsystem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.codephillip.app.automatedirrigationsystem.jsonmodels.users.User;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiClient;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = SignInActivity.class.getSimpleName();
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserSignInTask authTask = null;

    private EditText phoneView;
    private EditText passwordView;
    private View progressView;
    private View loginFormView;
    boolean isRequestSuccessfull = false;
    public String key = "ServerRequest";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        phoneView = (EditText) findViewById(R.id.phone);
        passwordView = (EditText) findViewById(R.id.password);

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void attemptLogin() {
        if (authTask != null) {
            return;
        }

        // Reset errors.
        phoneView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String phoneNumber = phoneView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)){
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid phoneNumber address.
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneView.setError(getString(R.string.error_field_required));
            focusView = phoneView;
            cancel = true;
        } else if (!isPhoneNumberValid(phoneNumber)) {
            phoneView.setError(getString(R.string.error_invalid_phoneNumber));
            focusView = phoneView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            authTask = new UserSignInTask(phoneNumber, password);
            authTask.execute((Void) null);
        }
    }

    private boolean isPhoneNumberValid(String phone) {
        return phone.length() == 10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserSignInTask extends AsyncTask<Void, Void, Void> {

        private final String mPhoneNumber;
        private final String mPassword;


        public UserSignInTask(String phoneNumber, String password) {
            mPhoneNumber = phoneNumber;
            mPassword = password;        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.d(TAG, "doInBackground: "+ mPhoneNumber + "#" + mPassword);
                signInUser(mPhoneNumber, mPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private void signInUser(String phoneNumber, String password) {
            ApiInterface apiInterface = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);
            User user = new User(phoneNumber, password);

            Call<User> call = apiInterface.signInUser(ApiClient.BASE_URL + "/api/v1/users/" + "17", user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                    int statusCode = response.code();
                    Log.d(TAG, "onResponse: #" + statusCode);
                    saveServerResponse(statusCode == 202);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                }
            });
        }

        @Override
        protected void onPostExecute(Void runagom) {
            authTask = null;
            showProgress(false);

            Log.d(TAG, "onPostExecute: " + getServerResponse());

            if (getServerResponse()) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            showProgress(false);
        }
    }

    private void saveServerResponse(boolean isRequestSuccessfull) {
        Log.d(TAG, "saveServerResponse: " + isRequestSuccessfull);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, isRequestSuccessfull);
        editor.apply();
    }

    private boolean getServerResponse() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRequestSuccessfull = prefs.getBoolean(key, false);
        Log.d("PREF# ", String.valueOf(isRequestSuccessfull));
        return isRequestSuccessfull;
    }
}

