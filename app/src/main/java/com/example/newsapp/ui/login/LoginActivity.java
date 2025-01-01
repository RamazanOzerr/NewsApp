package com.example.newsapp.ui.login;

import static com.example.newsapp.util.Constants.INTENT_USER_LOGGED_IN;

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

    // Binding for the activity's UI components
    private ActivityLoginBinding binding;

    // Repository instance for authentication functionality
    private AuthRepository authRepository;

    private static final String TAG = "SIGN_IN";

    // Storage service for handling saved data (e.g., token)
    private StorageService storageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        listeners();
    }

    // Initialize repositories and services
    private void init(){
        authRepository = new AuthRepository();
        storageService = new StorageService(this);
    }

    // Set up listeners for the views in the layout
    private void listeners(){
        // Back button to close the login screen
        binding.signInImageBack.setOnClickListener(view -> finish());

        // Navigate to the sign-up activity
        binding.signInTextSignUp.setOnClickListener(view -> getToSignUpActivity());

        // Validate the input and attempt sign-in on button click
        binding.signInButton.setOnClickListener(view -> validateSignIn());
    }

    // Validate the email and password input fields
    private void validateSignIn(){
        // Start loading animation to indicate process
        startLoadingAnimation();

        // Check if email or password is empty
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

        // Retrieve email and password from input fields
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
        // Proceed with the sign-in process
        signIn(email, password);
    }

    // Perform sign-in by calling the login method from the AuthRepository
    private void signIn(String email, String passport){
        // Call the login method and handle success or failure
        authRepository.login(email, passport, new AuthRepository.AuthCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                // Save user ID to shared preferences (token)
                int id = response.getId();
                storageService.saveString(Constants.TOKEN_KEY, String.valueOf(id));

                // Navigate to the main activity on successful login
                getToMainActivity();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    // Navigate to the MainActivity after successful login
    private void getToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(INTENT_USER_LOGGED_IN, true); // Pass logged-in state
        startActivity(intent);
        finish();
    }

    // Navigate to the SignUpActivity
    private void getToSignUpActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    // Start loading animation to show progress during login
    private void startLoadingAnimation(){
        binding.animationViewLoadingSignIn.setVisibility(View.VISIBLE);
        binding.animationViewLoadingSignIn.playAnimation();
    }

    // Stop loading animation once the process is complete
    private void stopLoadingAnimation(){
        binding.animationViewLoadingSignIn.setVisibility(View.GONE);
        binding.animationViewLoadingSignIn.pauseAnimation();
    }

}