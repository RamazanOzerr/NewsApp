package com.example.newsapp;

import static com.example.newsapp.util.Constants.INTENT_USER_LOGGED_IN;

import android.os.Bundle;

import com.example.newsapp.service.StorageService;
import com.example.newsapp.util.Constants;
import com.example.newsapp.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.newsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private StorageService storageService;
    private boolean isUserLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        // Set up the navigation controller for the bottom navigation view
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Set up the toolbar as the app bar
        setSupportActionBar(binding.toolBar);

        // Initialize storage service
        storageService = new StorageService(this);
    }

    // Initialize variables and check if the user is logged in
    private void init(){
        // Retrieve the user login status from the intent
        Intent intent = getIntent();
        isUserLoggedIn = intent.getBooleanExtra(INTENT_USER_LOGGED_IN, false);
    }

    // Method to handle user sign-out
    private void signOut(){
        storageService.remove(Constants.TOKEN_KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_app_bar, menu);

        // Find the menu item
        MenuItem menuItem = menu.findItem(R.id.log_out);

        // Set the listener
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle sign out
                signOut();
                Util.createShortToast(MainActivity.this, "signed out successfully");
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    // Prepare the options menu before displaying it
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.log_out);

        // If the user is not logged in, hide the logout item
        if (item != null && !isUserLoggedIn) {
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);

    }
}