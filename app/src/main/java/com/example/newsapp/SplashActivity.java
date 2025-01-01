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

        // Start the loading animation while the app is checking the user's login status
        startLoadingAnimation();

        // Initialize the storage service to retrieve data from local storage
        StorageService storageService = new StorageService(this);

        // Get the stored token, which indicates whether the user is logged in
        String token = storageService.getString(Constants.TOKEN_KEY, null);
        Log.d(TAG, "onCreate: user id: " + token);

        // Check if the token is available (meaning the user is already signed in)
        if(token != null){
            // If the user is logged in, navigate to the main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(INTENT_USER_LOGGED_IN, true); // Pass login status as an intent extra
            startActivity(intent); // Start the main activity
            stopLoadingAnimation();  // Stop the loading animation
            finish();  // Close the splash activity
        } else {
            // If the user is not logged in, navigate to the onboarding activity
            Intent intent = new Intent(this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Method to start the loading animation (visible and playing)
    private void startLoadingAnimation(){
        binding.animationViewLoadingSplash.setVisibility(View.VISIBLE);
        binding.animationViewLoadingSplash.playAnimation();
    }

    // Method to stop the loading animation (hidden and paused)
    private void stopLoadingAnimation(){
        binding.animationViewLoadingSplash.setVisibility(View.GONE);
        binding.animationViewLoadingSplash.pauseAnimation();
    }
}