package com.codephillip.app.automatedirrigationsystem.retrofit;

import com.codephillip.app.automatedirrigationsystem.jsonmodels.crops.Crops;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.feedbacks.Feedback;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.metrics.Metrics;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.users.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * Created by codephillip on 22/12/16.
 */

public interface ApiInterface {

    @GET("/api/v1/metrics?format=json")
    Call<Metrics> allMetrics();

    @GET("/api/v1/crops?format=json")
    Call<Crops> allCrops();

    @PUT
    Call<User> updateUser(@Url String url, @Body User user);

    @POST("/api/v1/users")
    Call<User> createUser(@Body User user);

    @PUT("/api/v1/users")
    Call<User> signInUser(@Body User user);

    @POST("/api/v1/feedbacks")
    Call<Feedback> createFeedback(@Body Feedback feedback);
}
