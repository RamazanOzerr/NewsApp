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

//        if(userId == null){
//            viewModel.getAllArticlesFromRemote(Integer.parseInt(userId)).observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
//                @Override
//                public void onChanged(List<Article> articles) {
//                    if (articles != null) {
//                        if(articles.isEmpty()){
//                            noDataAvailable();
//                        } else {
//                            newsAdapter.updateArticles(articles);
//                        }
//                    }
//                }
//            });
//        } else {
//
//        }

        viewModel.getAllArticles().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                if(articles.isEmpty()){
                    noDataAvailable();
                } else {
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

    private void handleSwipeToDelete(){
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
//                newsAdapter.notifyItemRemoved(position);

                // show a snackbar to undo deletion
                 Snackbar.make(binding.rvSavedNews, "Article deleted", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewModel.insertOrUpdateArticle(article);
                                newsAdapter.addArticleAt(position, article);
                                setAsDataAvailable();
                            }
                        })
                        .show();

                if(newsAdapter.getItemCount() == 0){
                    noDataAvailable();
                }
            }
        });

        itemTouchHelper.attachToRecyclerView(binding.rvSavedNews);
    }

    private void noDataAvailable(){
        binding.rvSavedNews.setVisibility(View.GONE);
        binding.noDataLayout.getRoot().setVisibility(View.VISIBLE);
    }

    private void setAsDataAvailable(){
        binding.rvSavedNews.setVisibility(View.VISIBLE);
        binding.noDataLayout.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        viewModel.getAllArticles().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                newsAdapter.updateArticles(articles);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}