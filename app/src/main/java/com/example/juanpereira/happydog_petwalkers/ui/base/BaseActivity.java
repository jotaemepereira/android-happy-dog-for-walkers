package com.example.juanpereira.happydog_petwalkers.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.juanpereira.happydog_petwalkers.HappyDogApplication;
import com.example.juanpereira.happydog_petwalkers.di.components.ActivityComponent;
import com.example.juanpereira.happydog_petwalkers.di.components.DaggerActivityComponent;
import com.example.juanpereira.happydog_petwalkers.di.modules.ActivityModule;

public class BaseActivity extends AppCompatActivity{

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(HappyDogApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
}
