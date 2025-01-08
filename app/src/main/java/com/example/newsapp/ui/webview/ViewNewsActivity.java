package com.example.newsapp.ui.webview;

import static com.example.newsapp.util.Constants.NEWS_URL_INTENT;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newsapp.R;
import com.example.newsapp.databinding.ActivityViewNewsBinding;
import com.example.newsapp.util.Util;
import android.content.Intent;

public class ViewNewsActivity extends AppCompatActivity {

    private ActivityViewNewsBinding binding;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityViewNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    // Initialize the toolbar and get the URL to load
    private void init(){

        setSupportActionBar(binding.toolBar);
        // Set the navigation click listener to finish the activity
        binding.toolBar.setNavigationOnClickListener(view -> {
            finish();
        });

        // Retrieve the URL from the intent
        url = getIntent().getStringExtra(NEWS_URL_INTENT);

        // If URL is valid, load the news article in the WebView
        if(url != null){
            viewNews(url);
        } else {
            // Display an error toast if URL is not provide
            Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT).show();
        }

    }

    // Load the news article in the WebView
    private void viewNews(String url){
        // Configure WebView settings
        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setDomStorageEnabled(true); // Enable DOM storage for modern websites

        // Ensure links and redirects open within the WebView
        binding.webview.setWebViewClient(new WebViewClient());

        // Load a news website
        binding.webview.loadUrl(url); // Replace with your news URL
    }

    // Share the news article URL with other apps
    private void shareNews(){
        if(url == null){
            Util.createShortToast(this, "an error occurred, please try again later");
            return;
        }
        // Create the share intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this news article: " + url);

        // Launch the share chooser
        startActivity(Intent.createChooser(shareIntent, "Share news via"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.appbar_view_news, menu);

        // Find the menu item
        MenuItem menuItem = menu.findItem(R.id.share);

        // Set the listener
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle sign out
                shareNews();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }
}