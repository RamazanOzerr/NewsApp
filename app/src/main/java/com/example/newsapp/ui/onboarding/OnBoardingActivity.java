package com.example.newsapp.ui.onboarding;

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
        binding.tvOnboardingAnonymousUser.setOnClickListener(view -> getToMainActivity());
        binding.signInButton.setOnClickListener(view -> getToLoginActivity());
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

    // get to main activity
    private void getToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}