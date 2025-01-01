package com.example.newsapp.ui.savedNews;

import static com.example.newsapp.util.URL.API_KEY;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.adapter.NewsAdapter;
import com.example.newsapp.adapter.SavedNewsAdapter;
import com.example.newsapp.databinding.FragmentSavedNewsBinding;
import com.example.newsapp.model.Article;
import com.example.newsapp.util.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class SavedNewsFragment extends Fragment {

//    private FragmentDashboardBinding binding;
    private FragmentSavedNewsBinding binding;

    private SavedNewsAdapter newsAdapter;
    private SavedNewsViewModel viewModel;
    
    private static final String TAG = "SAVED FRAGMENT";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();
        // Observe the LiveData from the ViewModel to update the list of saved articles
        viewModel.getAllArticles().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                if(articles.isEmpty()){
                    // If no articles, show the "no data" message
                    noDataAvailable();
                } else {
                    // Otherwise, update the adapter with the articles
                    newsAdapter.updateArticles(articles);
                }
            }
        });

        handleSwipeToDelete();

        return root;
    }

    private void init(){
        viewModel =
                new ViewModelProvider(requireActivity()).get(SavedNewsViewModel.class);

        binding.rvSavedNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter = new SavedNewsAdapter(new ArrayList<>(), getContext());
        binding.rvSavedNews.setAdapter(newsAdapter);

    }

    // Set up swipe-to-delete functionality for the RecyclerView (swipe left to delete)
    private void handleSwipeToDelete(){

        // Create ItemTouchHelper for swipe gestures
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Article article = newsAdapter.getArticles().get(position);

                // Remove the article from the database
                viewModel.deleteArticle(article);
                newsAdapter.removeArticleAt(position);

                // show a snack bar to undo deletion
                 Snackbar.make(binding.rvSavedNews, "Article deleted", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Undo the deletion by re-inserting the article
                                viewModel.insertOrUpdateArticle(article);
                                newsAdapter.addArticleAt(position, article);
                                setAsDataAvailable();
                            }
                        })
                        .show();

                // If no more articles, show the "no data" layout
                if(newsAdapter.getItemCount() == 0){
                    noDataAvailable();
                }
            }
        });

        // Attach the ItemTouchHelper to the RecyclerView
        itemTouchHelper.attachToRecyclerView(binding.rvSavedNews);
    }

    // Display "no data available" message and hide the RecyclerView
    private void noDataAvailable(){
        binding.rvSavedNews.setVisibility(View.GONE);
        binding.noDataLayout.getRoot().setVisibility(View.VISIBLE);
    }

    // Show the RecyclerView and hide the "no data available" message
    private void setAsDataAvailable(){
        binding.rvSavedNews.setVisibility(View.VISIBLE);
        binding.noDataLayout.getRoot().setVisibility(View.GONE);
    }

    // Observe the articles in the ViewModel when the fragment resumes
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        viewModel.getAllArticles().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                // Update the adapter when the data changes
                newsAdapter.updateArticles(articles);
            }
        });
    }

    // Clean up resources when the fragment's view is destroyed
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}