package com.example.juanpereira.happydog_petwalkers.di.modules;

import android.app.Application;
import android.content.Context;

import com.example.juanpereira.happydog_petwalkers.di.ApplicationContext;
import com.example.juanpereira.happydog_petwalkers.networking.NetworkAdapter;
import com.example.juanpereira.happydog_petwalkers.networking.NetworkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    protected final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    NetworkService provideNetworkService() {
        return NetworkAdapter.getNetworkService();
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }
}