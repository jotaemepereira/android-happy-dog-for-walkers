package com.example.juanpereira.happydog_petwalkers;

import android.app.Application;
import android.content.Context;


import com.example.juanpereira.happydog_petwalkers.di.components.ApplicationComponent;
import com.example.juanpereira.happydog_petwalkers.di.components.DaggerApplicationComponent;
import com.example.juanpereira.happydog_petwalkers.di.modules.ApplicationModule;

public class HappyDogApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static HappyDogApplication get(Context context) {
        return (HappyDogApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
