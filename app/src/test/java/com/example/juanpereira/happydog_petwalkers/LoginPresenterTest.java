package com.example.juanpereira.happydog_petwalkers;

import com.example.juanpereira.happydog_petwalkers.models.DataManager;
import com.example.juanpereira.happydog_petwalkers.models.LoginBody;
import com.example.juanpereira.happydog_petwalkers.networking.NetworkService;
import com.example.juanpereira.happydog_petwalkers.ui.login.LoginPresenter;
import com.example.juanpereira.happydog_petwalkers.ui.login.LoginView;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock LoginView loginView;
    @Mock NetworkService networkService;

    private LoginPresenter testSubject;

    @BeforeClass
    public static void setupClass() {
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        DataManager dataManager = new DataManager(networkService);
        testSubject = new LoginPresenter(dataManager);
        testSubject.attachView(loginView);
    }

    @After
    public void detachView() {
        testSubject.detachView();
    }

    @Test
    public void onInvalidEmail() {
        testSubject.login("invalidEmail", "password");
        verify(loginView).onLoginError("Invalid email");
    }

    @Test
    public void onEmptyEmail() {
        testSubject.login("", "password");
        verify(loginView).onLoginError("Fields cannot be empty");
    }

    @Test
    public void onEmptyPassword() {
        testSubject.login("email@test.com", "");
        verify(loginView).onLoginError("Fields cannot be empty");
    }

    @Test
    public void onSuccessfulLogin() {
        stubDataInDataManager(Observable.just(Response.success(Void.class)));

        testSubject.login("email@test.com", "password");

        verify(loginView).setLoginButtonEnabled(false);
        verify(loginView).showProgress(true);
        verify(loginView).setLoginButtonEnabled(true);
        verify(loginView).showProgress(false);
        verify(loginView).onLoginSuccessful();
    }

    @Test
    public void onInvalidCredentialsError() {
        stubDataInDataManager(
                Observable.just(
                        Response.error(
                                404,
                                ResponseBody.create(MediaType.parse("application/json"), "")
                        )
                )
        );

        testSubject.login("email@test.com", "password");

        verify(loginView).setLoginButtonEnabled(false);
        verify(loginView).showProgress(true);
        verify(loginView).setLoginButtonEnabled(true);
        verify(loginView).showProgress(false);
        verify(loginView).onLoginError("Invalid credentials");
    }

    @Test
    public void onErrorLogin() {
        stubDataInDataManager(Observable.error(new Throwable()));

        testSubject.login("email@test.com", "password");

        verify(loginView).setLoginButtonEnabled(false);
        verify(loginView).showProgress(true);
        verify(loginView).setLoginButtonEnabled(true);
        verify(loginView).showProgress(false);
        verify(loginView).onLoginError("There was an internal error. Please try again.");
    }

    private void stubDataInDataManager(Observable observable) {
        when(networkService.login(any(LoginBody.class))).thenReturn(observable);
    }
}
