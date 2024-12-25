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

    private FragmentSearchNewsBinding binding;
    private SearchNewsViewModel searchNewsViewModel;
    private NewsAdapter newsAdapter;

    private SavedNewsViewModel savedNewsViewModel;

    private static final long SEARCH_NEWS_TIME_DELAY = 500; // Delay in milliseconds
    private Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSearchNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.rvSearchNews.setLayoutManager(new LinearLayoutManager(getContext()));

        savedNewsViewModel = new ViewModelProvider(this).get(SavedNewsViewModel.class);

        newsAdapter = new NewsAdapter(new ArrayList<>(), getContext());
        binding.rvSearchNews.setAdapter(newsAdapter);

        searchNewsViewModel =
                new ViewModelProvider(this).get(SearchNewsViewModel.class);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                // Clear previous results when a new query is entered
                newsAdapter.updateArticles(new ArrayList<>());

                searchRunnable = () -> {
                    if (newText != null && !newText.trim().isEmpty()) {
                        performSearch(newText);
                    }
                };

                searchHandler.postDelayed(searchRunnable, SEARCH_NEWS_TIME_DELAY);
                return true;

            }
        });



        return root;
    }
    private void performSearch(String query) {
        // Call the ViewModel method or any other logic to search for news
        searchNewsViewModel.getNewsList(query, API_KEY).observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if(articles != null){
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
                List<Article> savedArticles = newsAdapter.getSavedArticles();
                if(savedArticles.isEmpty()){
                    return;
                }

                for (Article article : savedArticles) {
                    savedNewsViewModel.insertOrUpdateArticle(article);
                }

                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Articles saved successfully!", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                // Log error
                e.printStackTrace();

                // Notify the user on the main thread
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Failed to save articles!", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}