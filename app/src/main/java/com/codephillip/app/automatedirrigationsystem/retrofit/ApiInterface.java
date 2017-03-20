package com.codephillip.app.automatedirrigationsystem.retrofit;

import com.codephillip.app.automatedirrigationsystem.jsonmodels.feedbacks.Feedback;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.metrics.Metrics;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by codephillip on 22/12/16.
 */

public interface ApiInterface {

    @GET("/api/v1/metrics?format=json")
    Call<Metrics> allMetrics();

    @POST("/api/v1/feedbacks")
    Call<Feedback> createFeedback(@Body Feedback feedback);
}
