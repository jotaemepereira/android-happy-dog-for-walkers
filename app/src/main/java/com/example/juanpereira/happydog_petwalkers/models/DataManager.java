package com.example.juanpereira.happydog_petwalkers.models;

import com.example.juanpereira.happydog_petwalkers.networking.NetworkService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

@Singleton
public class DataManager {

    private final NetworkService networkService;

    @Inject
    public DataManager(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Observable<Response<Void>> login(LoginBody loginBody) {
        return networkService.login(loginBody);
    }

}
