package com.example.juanpereira.happydog_petwalkers.networking;

import com.example.juanpereira.happydog_petwalkers.models.LoginBody;
import com.example.juanpereira.happydog_petwalkers.models.SignupBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkService {

    @POST("Usuarios")
    Call<Void> signUp(@Body SignupBody body);

    @POST("login")
    Call<Void> login(@Body LoginBody body);
}
