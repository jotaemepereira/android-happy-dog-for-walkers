package com.example.juanpereira.happydog_petwalkers.networking;

import com.example.juanpereira.happydog_petwalkers.models.LoginBody;
import com.example.juanpereira.happydog_petwalkers.models.SignupBody;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkService {

    @POST("Usuarios")
    Observable<Void> signUp(@Body SignupBody body);

    @POST("login")
    Observable<Response<Void>> login(@Body LoginBody body);
}
