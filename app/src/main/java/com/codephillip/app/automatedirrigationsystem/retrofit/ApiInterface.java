package com.codephillip.app.automatedirrigationsystem.retrofit;

import com.codephillip.app.automatedirrigationsystem.jsonmodels.metrics.Metrics;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by codephillip on 22/12/16.
 */

public interface ApiInterface {

    @GET("/api/v1/metrics?format=json")
    Call<Metrics> allMetrics();
}
