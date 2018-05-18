package com.example.juanpereira.happydog_petwalkers.ui.login;

import com.example.juanpereira.happydog_petwalkers.models.DataManager;
import com.example.juanpereira.happydog_petwalkers.models.LoginBody;
import com.example.juanpereira.happydog_petwalkers.networking.NetworkService;
import com.example.juanpereira.happydog_petwalkers.ui.base.BasePresenter;
import com.example.juanpereira.happydog_petwalkers.utils.TextUtils;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginView view;
    private Disposable disposable;
    private DataManager dataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
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

        if (disposable != null) disposable.dispose();
    }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.onLoginError("Fields cannot be empty");
        } else if (!TextUtils.isValidEmail(email)) {
            view.onLoginError("Invalid email");
        } else {
            view.showProgress(true);
            view.setLoginButtonEnabled(false);
            disposable = dataManager.login(createLoginBody(email, password))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Response<Void>>() {
                        @Override
                        public void accept(Response<Void> voidResponse) throws Exception {
                            view.showProgress(false);
                            view.setLoginButtonEnabled(true);

                            if (voidResponse.isSuccessful()) {
                                view.onLoginSuccessful();
                            } else {
                                view.onLoginError("Invalid credentials");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.showProgress(false);
                            view.setLoginButtonEnabled(true);
                            view.onLoginError("There was an internal error. Please try again.");
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
