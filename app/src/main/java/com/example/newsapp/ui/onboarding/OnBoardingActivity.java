package com.example.newsapp.ui.onboarding;

import static com.example.newsapp.util.Constants.INTENT_USER_LOGGED_IN;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newsapp.MainActivity;
import com.example.newsapp.R;
import com.example.newsapp.databinding.ActivityOnBoardingBinding;
import com.example.newsapp.ui.login.LoginActivity;
import com.example.newsapp.ui.signup.SignUpActivity;

public class OnBoardingActivity extends AppCompatActivity {

    private ActivityOnBoardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listeners();
    }

    // set listeners
    private void listeners(){
        // Listener for the "Continue as anonymous" option (skip login/signup)
        binding.tvOnboardingAnonymousUser.setOnClickListener(view -> getToMainActivity());

        // Listener for the "Sign In" button to navigate to the login screen
        binding.signInButton.setOnClickListener(view -> getToLoginActivity());

        // Listener for the "Create Account" button to navigate to the sign-up screen
        binding.createAccountButton.setOnClickListener(view -> getToSignUpActivity());
    }

    // get to sign up activity
    private void getToSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


    // get to login activity
    private void getToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Navigate to the Main activity as an anonymous user (not logged in)
    private void getToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        // Pass the flag indicating the user is not logged in
        intent.putExtra(INTENT_USER_LOGGED_IN, false);
        startActivity(intent);
        finish(); // Close the onboarding activity
    }
}