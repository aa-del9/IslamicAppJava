package com.oop_project.cs212.islamicapp.interfaces;

import com.oop_project.cs212.islamicapp.model.Athkar;
import com.oop_project.cs212.islamicapp.BuildConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface RetrofitApiCaller {
    @Headers("X-Api-Key: "+ BuildConfig.API_KEY)
    @GET()
    Call<Athkar> getData(@Url String url);
}
