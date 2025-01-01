package com.example.newsapp.ui.searchNews;

import static com.example.newsapp.util.URL.API_KEY;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.newsapp.adapter.NewsAdapter;
import com.example.newsapp.databinding.FragmentSearchNewsBinding;
import com.example.newsapp.model.Article;
import com.example.newsapp.ui.savedNews.SavedNewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsFragment extends Fragment {

    // Binding to access views in the layout
    private FragmentSearchNewsBinding binding;

    // ViewModel for searching news articles
    private SearchNewsViewModel searchNewsViewModel;

    // Adapter for displaying news articles in RecyclerView
    private NewsAdapter newsAdapter;

    // ViewModel for managing saved articles
    private SavedNewsViewModel savedNewsViewModel;

    // Constant for search delay (in milliseconds)
    private static final long SEARCH_NEWS_TIME_DELAY = 500;

    // Handler to manage the search delay logic
    private final Handler searchHandler = new Handler(Looper.getMainLooper());

    // Runnable that handles search execution after the delay
    private Runnable searchRunnable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSearchNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize RecyclerView for displaying search results
        binding.rvSearchNews.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize ViewModel for saved articles
        savedNewsViewModel = new ViewModelProvider(this).get(SavedNewsViewModel.class);

        // Initialize the adapter for the RecyclerView and set it
        newsAdapter = new NewsAdapter(new ArrayList<>(), getContext());
        binding.rvSearchNews.setAdapter(newsAdapter);

        // Initialize ViewModel for search functionality
        searchNewsViewModel =
                new ViewModelProvider(this).get(SearchNewsViewModel.class);

        // Set listener for the SearchView to handle text input
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Remove previous search callbacks if new text is entered
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                // Clear previous results when a new query is entered
                newsAdapter.updateArticles(new ArrayList<>());

                // Runnable to perform search after the specified delay
                searchRunnable = () -> {
                    if (newText != null && !newText.trim().isEmpty()) {
                        performSearch(newText);
                    }
                };

                // Post the search request after the delay
                searchHandler.postDelayed(searchRunnable, SEARCH_NEWS_TIME_DELAY);
                return true;

            }
        });

        return root;
    }

    // Method to perform the search for news articles based on the query
    private void performSearch(String query) {
        // Call the ViewModel method or any other logic to search for news
        searchNewsViewModel.getNewsList(query, API_KEY).observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if(articles != null){
                    // Update the RecyclerView with the fetched articles
                    newsAdapter.updateArticles(articles);
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        new Thread(() -> {
            try {
                // Save the articles that were liked or saved by the user
                List<Article> savedArticles = newsAdapter.getSavedArticles();
                if(savedArticles.isEmpty()){
                    return;
                }

                // Save each article to the database
                for (Article article : savedArticles) {
                    savedNewsViewModel.insertOrUpdateArticle(article);
                }

                // Notify the user that articles were saved successfully
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Articles saved successfully!", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                // Log error
                e.printStackTrace();

                // Notify the user on the main thread about failure
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Failed to save articles!", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up binding to prevent memory leaks
        binding = null;
    }
}