package com.codephillip.app.automatedirrigationsystem.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    //use http://10.0.3.2 when using genymotion emulator
//    public static final String BASE_URL = "http://10.0.3.2:8000";
    //production url
    public static final String BASE_URL = "https://ais-api.herokuapp.com";
    private static Retrofit retrofit = null;


    public static Retrofit getClient(String url) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
