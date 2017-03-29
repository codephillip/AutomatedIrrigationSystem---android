package com.codephillip.app.automatedirrigationsystem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codephillip.app.automatedirrigationsystem.jsonmodels.users.User;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.users.Users;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiClient;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;

import static com.codephillip.app.automatedirrigationsystem.R.id.phone;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = SignInActivity.class.getSimpleName();
    private EditText phoneView;
    private EditText passwordView;
    private TextView createAccountView;
    private View progressView;
    private View loginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Utils.getInstance();

        phoneView = (EditText) findViewById(phone);
        passwordView = (EditText) findViewById(R.id.password);

        createAccountView = (TextView) findViewById(R.id.create_account);
        createAccountView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

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
        // Reset errors.
        phoneView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String phoneNumber = phoneView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
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
            try {
                if (Utils.isConnectedToInternet(this))
                    signInUser(phoneNumber, password);
                else
                    Toast.makeText(this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isPhoneNumberValid(String phone) {
        return phone.length() == 10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    private void signInUser(String phoneNumber, String password) {
        ApiInterface apiInterface = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);
        User user = new User(phoneNumber, password);

        Call<Users> call = apiInterface.signInUser(user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, retrofit2.Response<Users> response) {
                int statusCode = response.code();
                Users user = response.body();
                Utils.user = new User(user.getUsers().get(0).getName(), user.getUsers().get(0).getAddress(), user.getUsers().get(0).getPhoneNumber(), user.getUsers().get(0).getPassword(), user.getUsers().get(0).getCrop());
                processResult((statusCode == 202));
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    private void processResult(boolean success) {
        showProgress(false);
        if (success) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            passwordView.setError(getString(R.string.error_incorrect_password));
            passwordView.requestFocus();
        }
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
}

