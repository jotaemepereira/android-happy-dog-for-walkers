package com.example.juanpereira.happydog_petwalkers.di.components;

import android.app.Application;
import android.content.Context;

import com.example.juanpereira.happydog_petwalkers.HappyDogApplication;
import com.example.juanpereira.happydog_petwalkers.di.ApplicationContext;
import com.example.juanpereira.happydog_petwalkers.di.modules.ApplicationModule;
import com.example.juanpereira.happydog_petwalkers.networking.NetworkService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext Context context();
    Application application();
    NetworkService networkService();
}
