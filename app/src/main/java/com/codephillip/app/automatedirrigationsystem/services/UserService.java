package com.codephillip.app.automatedirrigationsystem.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.codephillip.app.automatedirrigationsystem.Utils;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.users.User;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiClient;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class UserService extends IntentService {

    private static final String TAG = UserService.class.getSimpleName();

    public UserService() {
        super("UserService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            updateUserData(intent.getIntExtra("crop_id", 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUserData(int crop_id) {
        ApiInterface apiInterface = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);
        User user = new User(crop_id);
        String url = ApiClient.BASE_URL + "/api/v1/users/" + Utils.user.getId();
        Log.d(TAG, "updateUserData: " + url);
        Call<User> call = apiInterface.updateUser(url, user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                int statusCode = response.code();
                Log.d(TAG, "onResponse: #" + statusCode);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }
}
