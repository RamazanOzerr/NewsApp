package com.example.newsapp;

import static com.example.newsapp.util.Constants.INTENT_USER_LOGGED_IN;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newsapp.databinding.ActivitySplashBinding;
import com.example.newsapp.service.StorageService;
import com.example.newsapp.ui.onboarding.OnBoardingActivity;
import com.example.newsapp.util.Constants;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SPLASH";
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        startLoadingAnimation();
        StorageService storageService = new StorageService(this);

        String token = storageService.getString(Constants.TOKEN_KEY, null);
        Log.d(TAG, "onCreate: user id: " + token);

        // user already signed in
        if(token != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(INTENT_USER_LOGGED_IN, true);
            startActivity(intent);
            stopLoadingAnimation();
            finish();
        } else {
            Intent intent = new Intent(this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void startLoadingAnimation(){
        binding.animationViewLoadingSplash.setVisibility(View.VISIBLE);
        binding.animationViewLoadingSplash.playAnimation();
    }

    private void stopLoadingAnimation(){
        binding.animationViewLoadingSplash.setVisibility(View.GONE);
        binding.animationViewLoadingSplash.pauseAnimation();
    }
}