package com.example.juanpereira.happydog_petwalkers.utils.di;

import com.example.juanpereira.happydog_petwalkers.di.components.ApplicationComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
