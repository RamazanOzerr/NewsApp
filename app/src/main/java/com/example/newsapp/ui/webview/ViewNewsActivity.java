package com.example.newsapp.ui.webview;

import static com.example.newsapp.util.Constants.NEWS_URL_INTENT;

import android.os.Bundle;
import android.view.View;
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

public class ViewNewsActivity extends AppCompatActivity {

    private ActivityViewNewsBinding binding;

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

    private void init(){

        setSupportActionBar(binding.toolBar);
        binding.toolBar.setNavigationOnClickListener(view -> {
            finish();
        });

        String url = getIntent().getStringExtra(NEWS_URL_INTENT);
        if(url != null){
            viewNews(url);
        } else {
            Toast.makeText(this, "an error occured", Toast.LENGTH_SHORT).show();
        }

    }

    private void viewNews(String url){
        // Configure WebView settings
        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setDomStorageEnabled(true); // Enable DOM storage for modern websites

        // Ensure links and redirects open within the WebView
        binding.webview.setWebViewClient(new WebViewClient());

        // Load a news website
        binding.webview.loadUrl(url); // Replace with your news URL
    }
}