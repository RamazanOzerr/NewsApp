package com.example.newsapp.ui.login;

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

import com.example.newsapp.MainActivity;
import com.example.newsapp.R;
import com.example.newsapp.databinding.ActivityLoginBinding;
import com.example.newsapp.model.auth.LoginResponse;
import com.example.newsapp.repository.AuthRepository;
import com.example.newsapp.service.StorageService;
import com.example.newsapp.ui.signup.SignUpActivity;
import com.example.newsapp.util.Constants;
import com.example.newsapp.util.Util;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthRepository authRepository;
    private static final String TAG = "SIGN_IN";
    private StorageService storageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        listeners();
    }

    private void init(){
        authRepository = new AuthRepository();
        storageService = new StorageService(this);
    }

    private void listeners(){
        // set back button
        binding.signInImageBack.setOnClickListener(view -> finish());

        binding.signInTextSignUp.setOnClickListener(view -> getToSignUpActivity());

        binding.signInButton.setOnClickListener(view -> validateSignIn());
    }

    private void validateSignIn(){
        startLoadingAnimation();

        if(binding.signInEdittextEmail.getText() == null){
            Util.createShortToast(this, "an unknown error occurred, please try again later");
            stopLoadingAnimation();
            return;
        }
        if(binding.signInEdittextPassword.getText() == null){
            Util.createShortToast(this, "an unknown error occurred, please try again later");
            stopLoadingAnimation();
            return;
        }

        String email = binding.signInEdittextEmail.getText().toString().trim();
        String password = binding.signInEdittextPassword.getText().toString().trim();
        Log.d(TAG, "validateSignIn: " + email + " " + password);

        // check if email is entered
        if(TextUtils.isEmpty(email)){
            Log.d(TAG, "validateSignUp: email is not entered");
            binding.signInEdittextEmail.setError("You must enter your email");
            stopLoadingAnimation();
            return;
        }

        // check if password is entered
        if(TextUtils.isEmpty(password)){
            Log.d(TAG, "validateSignUp: password is not entered");
            binding.signInEdittextPassword.setError("You must enter a password");
            stopLoadingAnimation();
            return;
        }

        Log.d(TAG, "validateSignIn: valid input");
        signIn(email, password);
    }

    private void signIn(String email, String passport){
        // todo: write call back and get to main page
        authRepository.login(email, passport, new AuthRepository.AuthCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                int id = response.getId();
                storageService.saveString(Constants.TOKEN_KEY, String.valueOf(id));
                getToMainActivity();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getToSignUpActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void startLoadingAnimation(){
        binding.animationViewLoadingSignIn.setVisibility(View.VISIBLE);
        binding.animationViewLoadingSignIn.playAnimation();
    }

    private void stopLoadingAnimation(){
        binding.animationViewLoadingSignIn.setVisibility(View.GONE);
        binding.animationViewLoadingSignIn.pauseAnimation();
    }

}