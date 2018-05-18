package com.example.juanpereira.happydog_petwalkers.di.components;

import com.example.juanpereira.happydog_petwalkers.di.PerActivity;
import com.example.juanpereira.happydog_petwalkers.di.modules.ActivityModule;
import com.example.juanpereira.happydog_petwalkers.ui.login.LoginActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);
}
