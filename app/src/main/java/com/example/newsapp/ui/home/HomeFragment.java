package com.example.newsapp.ui.home;

import static com.example.newsapp.util.URL.API_KEY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.adapter.NewsAdapter;
import com.example.newsapp.databinding.FragmentHomeBinding;
import com.example.newsapp.model.Article;
import com.example.newsapp.ui.savedNews.SavedNewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "HOME_FRAGMENT";
    
    private FragmentHomeBinding binding;

    private NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;

    private SavedNewsViewModel savedNewsViewModel;

    /**
     * Inflates the layout for the fragment, sets up RecyclerView, ViewModel, and observers.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up RecyclerView with a LinearLayoutManager
        binding.rvHomeNews.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize SavedNewsViewModel to interact with saved articles
        savedNewsViewModel = new ViewModelProvider(requireActivity()).get(SavedNewsViewModel.class);

        // Initialize NewsAdapter and set it to RecyclerView
        newsAdapter = new NewsAdapter(new ArrayList<>(), getContext());
        binding.rvHomeNews.setAdapter(newsAdapter);

        // Initialize ViewModel to interact with news data
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        // Observe the LiveData from the ViewModel to get the news articles
        newsViewModel.getNewsList("us", API_KEY).observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                // Update the RecyclerView with the retrieved articles
                newsAdapter.updateArticles(articles);
                Log.d(TAG, "onCreateView: breaking news retrieved successfully");
            } else {
                // Show a message when no data is available
                noDataAvailable();
                Log.d(TAG, "onCreateView: articles are null");
            }
            // Dismiss progress bar after receiving the response
            dismissProgressBar();
        });

        return root;
    }

    /**
     * Hides the progress bar after the data is loaded.
     */
    private void dismissProgressBar(){
        binding.progressBarHome.setVisibility(View.GONE);
    }

    /**
     * Shows a layout indicating that no data is available.
     */
    private void noDataAvailable(){
        binding.rvHomeNews.setVisibility(View.GONE);
        binding.noDataLayout.getRoot().setVisibility(View.VISIBLE);
    }

    /**
     * Saves liked articles to the database when the fragment is stopped.
     * This method runs on a background thread to prevent blocking the UI thread.
     */
    @Override
    public void onStop() {
        super.onStop();
        new Thread(() -> {
            try {
                // Retrieve the list of saved articles from the adapter
                List<Article> savedArticles = newsAdapter.getSavedArticles();
                if(savedArticles.isEmpty()){
                    Log.d(TAG, "onStop: there is no article to be saved as liked");
                    return;
                }

                // Save each article in the database
                for (Article article : savedArticles) {
                    savedNewsViewModel.insertOrUpdateArticle(article);
                }
                Log.d(TAG, "onStop: all liked articles saved successfully");

                // Notify the user about the successful saving of articles
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Articles saved successfully!", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                // Log error
                e.printStackTrace();

                // Notify the user if saving articles failed
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Failed to save articles!", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    /**
     * Cleans up the binding when the fragment view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}