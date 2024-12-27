package com.example.newsapp.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newsapp.R;
import com.example.newsapp.databinding.ActivitySignUpBinding;
import com.example.newsapp.model.auth.LoginResponse;
import com.example.newsapp.model.auth.SignupResponse;
import com.example.newsapp.repository.AuthRepository;
import com.example.newsapp.ui.login.LoginActivity;
import com.example.newsapp.util.Util;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private static final String TAG = "SIGN_UP";
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        listeners();
    }

    private void init(){
        authRepository = new AuthRepository();
    }

    private void listeners(){
        // set back button
        binding.signUpImageBack.setOnClickListener(view -> finish());

        // get to walkthrough page
        binding.signUpButton.setOnClickListener(view -> validateSignUp());
    }

    private void validateSignUp(){
        // start loading animation
        startLoadingAnimation();

        if(binding.signupEdittextEmail.getText() == null){
            Util.createShortToast(this,
                    "an unknown error occurred, please try again later");
            stopLoadingAnimation();
            return;
        }
        if(binding.signUpEdittextPassword.getText() == null){
            Util.createShortToast(this,
                    "an unknown error occurred, please try again later");
            stopLoadingAnimation();
            return;
        }
        if(binding.signUpEdittextPasswordConfirm.getText() == null){
            Util.createShortToast(this,
                    "an unknown error occurred, please try again later");
            stopLoadingAnimation();
            return;
        }

        String email = binding.signupEdittextEmail.getText().toString().trim();
        String password = binding.signUpEdittextPassword.getText().toString().trim();
        String passwordConfirm = binding.signUpEdittextPasswordConfirm.getText().toString().trim();
        Log.d(TAG, "validateSignUp: " + email + " " + password + " " + passwordConfirm);

        // check if email is entered
        if(TextUtils.isEmpty(email)){
            Log.d(TAG, "validateSignUp: email is not entered");
            binding.signupEdittextEmail.setError("You must enter your email");
            stopLoadingAnimation();
            return;
        }

        // check if password is entered
        if(TextUtils.isEmpty(password)){
            Log.d(TAG, "validateSignUp: password is not entered");
            binding.signUpEdittextPassword.setError("You must enter a password");
            stopLoadingAnimation();
            return;
        }

        if(password.length() < 8){
            Log.d(TAG, "validateSignUp: password is less than 8 characters");
            binding.signUpEdittextPassword.setError("Your password must be at least 8 characters");
            stopLoadingAnimation();
            return;
        }

        if(password.length() > 20){
            Log.d(TAG, "validateSignUp: password is more than 20 characters");
            binding.signUpEdittextPassword.setError("Your password must be max 20 characters");
            stopLoadingAnimation();
            return;
        }

        // check if confirm password is entered
        if(TextUtils.isEmpty(passwordConfirm)){
            Log.d(TAG, "validateSignUp: confirm password is not entered");
            binding.signUpEdittextPasswordConfirm.setError("You must confirm your password");
            stopLoadingAnimation();
            return;
        }

        if(!password.equals(passwordConfirm)){
            Log.d(TAG, "validateSignUp: passwords do not match");
            binding.signUpEdittextPasswordConfirm.setError("Password must match");
            stopLoadingAnimation();
            return;
        }

        boolean isConfirmed = binding.checkboxChild.isChecked();
        if(!isConfirmed){
            Log.d(TAG, "validateSignUp: terms and conditions is not confirmed");
            binding.checkboxChild.setError("you must confirm terms and conditions");
            Util.createShortToast(this, "you must confirm terms and conditions");
            stopLoadingAnimation();
            return;
        }

        Log.d(TAG, "validateSignUp: valid input: "
                + email + " " + password + " " + passwordConfirm);
        signUp(email, password);
    }

    private void signUp(String email, String password){
        authRepository.signUp(email, password, new AuthRepository.AuthCallback<SignupResponse>() {
            @Override
            public void onSuccess(SignupResponse response) {
                getToLogin();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startLoadingAnimation(){
        binding.animationViewLoadingSignup.setVisibility(View.VISIBLE);
        binding.animationViewLoadingSignup.playAnimation();
    }

    private void stopLoadingAnimation(){
        binding.animationViewLoadingSignup.setVisibility(View.GONE);
        binding.animationViewLoadingSignup.pauseAnimation();
    }
}