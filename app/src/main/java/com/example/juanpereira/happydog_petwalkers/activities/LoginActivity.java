package com.example.juanpereira.happydog_petwalkers.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.juanpereira.happydog_petwalkers.R;
import com.example.juanpereira.happydog_petwalkers.models.BaseResponse;
import com.example.juanpereira.happydog_petwalkers.models.LoginBody;
import com.example.juanpereira.happydog_petwalkers.networking.NetworkAdapter;
import com.example.juanpereira.happydog_petwalkers.utils.SnackbarUtils;
import com.example.juanpereira.happydog_petwalkers.utils.TextUtils;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.emailInput) TextInputLayout emailInput;
    @BindView(R.id.passwordInput) TextInputLayout passwordInput;
    @BindView(R.id.progress) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setupLoadingIndicator();
    }

    private void setupLoadingIndicator() {
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
    }

    @OnClick(R.id.loginButton)
    public void onLoginAction() {
        if (areFieldsEmpty()) {
            SnackbarUtils.showErrorMessage(getString(R.string.empty_fields), findViewById(android.R.id.content));
        } else if (!TextUtils.isValidEmail(getEmail())) {
            SnackbarUtils.showErrorMessage(getString(R.string.invalid_email), findViewById(android.R.id.content));
        } else {
            doLogin();
        }
    }

    @OnClick(R.id.registrationText)
    public void onSignUpAction() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    private void doLogin() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Void> call = NetworkAdapter.getNetworkService().login(createLoginBody());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    goToMainActivity();
                } else {
                    SnackbarUtils.showErrorMessage(getString(R.string.invalid_credentials), findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                SnackbarUtils.showErrorMessage(getString(R.string.generic_error), findViewById(android.R.id.content));
            }
        });
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private LoginBody createLoginBody() {
        LoginBody loginBody = new LoginBody();
        loginBody.setEmail(getEmail());
        loginBody.setPassword(getPassword());

        return loginBody;
    }

    private boolean areFieldsEmpty() {
        return getEmail().isEmpty() || getPassword().isEmpty();
    }

    private String getEmail() {
        return emailInput.getEditText().getText().toString();
    }

    private String getPassword() {
        return passwordInput.getEditText().getText().toString();
    }
}

