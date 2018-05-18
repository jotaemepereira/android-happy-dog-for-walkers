package com.example.juanpereira.happydog_petwalkers.ui.login;

import com.example.juanpereira.happydog_petwalkers.models.LoginBody;
import com.example.juanpereira.happydog_petwalkers.networking.NetworkService;
import com.example.juanpereira.happydog_petwalkers.ui.base.BasePresenter;
import com.example.juanpereira.happydog_petwalkers.utils.TextUtils;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginView view;
    private Subscription subscription;
    private NetworkService service;

    @Inject
    public LoginPresenter(NetworkService service) {
        this.service = service;
    }

    @Override
    public void attachView(LoginView mvpView) {
        super.attachView(mvpView);
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        super.detachView();
        view = null;

        if (subscription != null) subscription.cancel();
    }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.onLoginError("Fields cannot be empty");
        } else if (!TextUtils.isValidEmail(email)) {
            view.onLoginError("Invalid email");
        } else {
            view.showProgress(true);
            view.setLoginButtonEnabled(false);
            service.login(createLoginBody(email, password)).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    view.showProgress(false);
                    view.setLoginButtonEnabled(true);

                    if (response.isSuccessful()) {
                        view.onLoginSuccessful();
                    } else {
                        view.onLoginError("Invalid credentials");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    view.showProgress(false);
                    view.setLoginButtonEnabled(true);
                    view.onLoginError("There was an error. Please try again.");
                }
            });
        }
    }

    private LoginBody createLoginBody(String email, String password) {
        LoginBody loginBody = new LoginBody();
        loginBody.setEmail(email);
        loginBody.setPassword(password);

        return loginBody;
    }
}
