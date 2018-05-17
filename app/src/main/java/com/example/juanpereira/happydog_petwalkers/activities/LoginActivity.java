package com.example.juanpereira.happydog_petwalkers.activities;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.juanpereira.happydog_petwalkers.R;
import com.example.juanpereira.happydog_petwalkers.utils.SnackbarUtils;
import com.example.juanpereira.happydog_petwalkers.utils.TextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.emailInput) TextInputLayout emailInput;
    @BindView(R.id.passwordInput) TextInputLayout passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginButton)
    public void onLoginAction() {
        if (areFieldsEmpty()) {
            SnackbarUtils.showErrorMessage("Fields cannot be empty", findViewById(android.R.id.content));
        } else if (!TextUtils.isValidEmail(getEmail())) {
            SnackbarUtils.showErrorMessage("Invalid email", findViewById(android.R.id.content));
        } else {
            doLogin();
        }
    }

    private void doLogin() {
        //TODO: Do login
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

