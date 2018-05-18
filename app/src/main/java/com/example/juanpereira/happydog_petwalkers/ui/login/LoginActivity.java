package com.example.juanpereira.happydog_petwalkers.ui.login;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.juanpereira.happydog_petwalkers.R;
import com.example.juanpereira.happydog_petwalkers.activities.MainActivity;
import com.example.juanpereira.happydog_petwalkers.ui.base.BaseActivity;
import com.example.juanpereira.happydog_petwalkers.ui.signup.SignUpActivity;
import com.example.juanpereira.happydog_petwalkers.models.LoginBody;
import com.example.juanpereira.happydog_petwalkers.networking.NetworkAdapter;
import com.example.juanpereira.happydog_petwalkers.utils.SnackbarUtils;
import com.example.juanpereira.happydog_petwalkers.utils.TextUtils;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.emailInput) TextInputLayout emailInput;
    @BindView(R.id.passwordInput) TextInputLayout passwordInput;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.loginButton) Button loginButton;

    @Inject LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activityComponent().inject(this);
        ButterKnife.bind(this);
        presenter.attachView(this);

        setupLoadingIndicator();
    }

    private void setupLoadingIndicator() {
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
    }

    @OnClick(R.id.loginButton)
    public void onLoginAction() {
        presenter.login(getEmail(), getPassword());
    }

    @OnClick(R.id.registrationText)
    public void onSignUpAction() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private String getEmail() {
        return emailInput.getEditText().getText().toString();
    }

    private String getPassword() {
        return passwordInput.getEditText().getText().toString();
    }

    @Override
    public void onLoginSuccessful() {
        goToMainActivity();
    }

    @Override
    public void onLoginError(String errorMessage) {
        SnackbarUtils.showErrorMessage(errorMessage, findViewById(android.R.id.content));
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setLoginButtonEnabled(boolean enabled) {
        loginButton.setEnabled(enabled);
    }
}

