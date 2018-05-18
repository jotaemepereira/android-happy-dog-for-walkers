package com.example.juanpereira.happydog_petwalkers.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.juanpereira.happydog_petwalkers.R;
import com.example.juanpereira.happydog_petwalkers.activities.MainActivity;
import com.example.juanpereira.happydog_petwalkers.models.SignupBody;
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

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.emailInput) TextInputLayout emailInput;
    @BindView(R.id.passwordInput) TextInputLayout passwordInput;
    @BindView(R.id.nameInput) TextInputLayout nameInput;
    @BindView(R.id.progress) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        setupLoadingIndicator();
        getSupportActionBar().setTitle("Sign up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupLoadingIndicator() {
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.registerButton)
    public void onSignUpAction() {
        if (areFieldsEmpty()) {
            SnackbarUtils.showErrorMessage(getString(R.string.empty_fields), findViewById(android.R.id.content));
        } else if (!TextUtils.isValidEmail(getEmail())) {
            SnackbarUtils.showErrorMessage(getString(R.string.invalid_email), findViewById(android.R.id.content));
        } else {
            signUp();
        }
    }

    private void signUp() {
        progressBar.setVisibility(View.VISIBLE);
        Call<Void> call = NetworkAdapter.getNetworkService().signUp(createSignUpBody());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    SnackbarUtils.showSuccessMessage("Success", findViewById(android.R.id.content));
                    goToMainActivity();
                } else {
                    SnackbarUtils.showErrorMessage(getString(R.string.email_taken), findViewById(android.R.id.content));
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

    private SignupBody createSignUpBody() {
        SignupBody signupBody = new SignupBody();
        signupBody.setEmail(getEmail());
        signupBody.setPassword(getPassword());
        signupBody.setName(getName());
        signupBody.setDogWalker(true);

        return signupBody;
    }

    private boolean areFieldsEmpty() {
        return getEmail().isEmpty() || getPassword().isEmpty() || getName().isEmpty();
    }

    private String getEmail() {
        return emailInput.getEditText().getText().toString();
    }

    private String getPassword() {
        return passwordInput.getEditText().getText().toString();
    }

    private String getName() {
        return nameInput.getEditText().getText().toString();
    }
}
