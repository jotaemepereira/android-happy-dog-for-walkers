package com.example.juanpereira.happydog_petwalkers.networking;

import com.example.juanpereira.happydog_petwalkers.models.BaseResponse;
import com.example.juanpereira.happydog_petwalkers.models.LoginBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkService {

    @POST("login")
    Call<BaseResponse> login(@Body LoginBody loginBody);
}
