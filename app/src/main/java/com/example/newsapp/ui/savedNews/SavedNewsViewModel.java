package com.example.newsapp.ui.savedNews;

import androidx.lifecycle.LiveData;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.model.Article;
import com.example.newsapp.repository.NewsLocalRepository;
import com.example.newsapp.repository.NewsRemoteRepository;

import java.util.List;

public class SavedNewsViewModel extends AndroidViewModel {

    // Repository for accessing local data (saved article
    private final NewsLocalRepository newsRepository;

    // LiveData to hold the list of saved articles
    private MutableLiveData<List<Article>> allArticles;

    // Constructor to initialize repositories and ViewModel
    public SavedNewsViewModel(Application application) {
        super(application);
        // Initialize the local repository with the application context
        newsRepository = new NewsLocalRepository(application);
    }

    // Get all saved articles from the local repository
    public LiveData<List<Article>> getAllArticles() {
        allArticles = newsRepository.getAllArticles();
        return allArticles;
    }

    // Insert or update a saved article in the local database
    public void insertOrUpdateArticle(Article article) {
        newsRepository.insertArticle(article);
        allArticles = newsRepository.getAllArticles();
    }

    // Delete a saved article from the local database
    public void deleteArticle(Article article) {
        newsRepository.deleteArticle(article);
    }

}
