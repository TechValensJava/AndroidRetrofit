package com.tv.androidretrofit;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by anjali on 3/3/16.
 */
public interface AuthenticationAPI {


    @POST("/api/create")
    public ResponseBean create(@Body RequestBean body);
}
