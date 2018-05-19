package com.example.juanpereira.happydog_petwalkers.utils;

import android.content.Context;

import com.example.juanpereira.happydog_petwalkers.HappyDogApplication;
import com.example.juanpereira.happydog_petwalkers.models.DataManager;
import com.example.juanpereira.happydog_petwalkers.utils.di.ApplicationTestModule;
import com.example.juanpereira.happydog_petwalkers.utils.di.DaggerTestComponent;
import com.example.juanpereira.happydog_petwalkers.utils.di.TestComponent;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestComponentRule implements TestRule {
    private TestComponent mTestComponent;
    private Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }


    public DataManager getMockDataManager() {
        return mTestComponent.dataManager();
    }

    private void setupDaggerTestComponentInApplication() {
        HappyDogApplication application = HappyDogApplication.get(mContext);
        mTestComponent = DaggerTestComponent
                .builder()
                .applicationTestModule(new ApplicationTestModule(application))
                .build();

        application.setComponent(mTestComponent);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    setupDaggerTestComponentInApplication();
                    base.evaluate();
                } finally {
                    mTestComponent = null;
                }
            }
        };

    }
}
