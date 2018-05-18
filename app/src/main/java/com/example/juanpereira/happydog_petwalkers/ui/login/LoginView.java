package com.example.juanpereira.happydog_petwalkers.ui.login;

import com.example.juanpereira.happydog_petwalkers.ui.base.MvpView;

public interface LoginView extends MvpView {

    void onLoginSuccessful();

    void onLoginError(String errorMessage);

    void showProgress(boolean show);

    void setLoginButtonEnabled(boolean enabled);
}
