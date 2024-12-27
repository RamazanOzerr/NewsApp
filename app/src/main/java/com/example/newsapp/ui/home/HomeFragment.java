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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.rvHomeNews.setLayoutManager(new LinearLayoutManager(getContext()));

        savedNewsViewModel = new ViewModelProvider(requireActivity()).get(SavedNewsViewModel.class);

        newsAdapter = new NewsAdapter(new ArrayList<>(), getContext());
        binding.rvHomeNews.setAdapter(newsAdapter);

        // Initialize ViewModel
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        //TODO: Change the location of the api key

        // Observe LiveData from ViewModel
        newsViewModel.getNewsList("us", API_KEY).observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                newsAdapter.updateArticles(articles);
                Log.d(TAG, "onCreateView: breaking news retrieved successfully");
            } else {
                noDataAvailable();
                Log.d(TAG, "onCreateView: articles are null");
            }
            dismissProgressBar();
//            if(articles != null && articles.isEmpty()){
//                noDataAvailable();
//                Log.d(TAG, "onCreateView: breaking news no data available");
//            }
            
        });

        return root;
    }

    private void dismissProgressBar(){
        binding.progressBarHome.setVisibility(View.GONE);
    }


    private void noDataAvailable(){
        binding.rvHomeNews.setVisibility(View.GONE);
        binding.noDataLayout.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        new Thread(() -> {
            try {
                List<Article> savedArticles = newsAdapter.getSavedArticles();
                if(savedArticles.isEmpty()){
                    Log.d(TAG, "onStop: there is no article to be saved as liked");
                    return;
                }

                for (Article article : savedArticles) {
                    savedNewsViewModel.insertOrUpdateArticle(article);
                }
                Log.d(TAG, "onStop: all liked articles saved successfully");

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