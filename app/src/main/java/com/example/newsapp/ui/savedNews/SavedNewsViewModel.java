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

    private final NewsLocalRepository newsRepository;
    private MutableLiveData<List<Article>> allArticles;

    public SavedNewsViewModel(Application application) {
        super(application);
        newsRepository = new NewsLocalRepository(application);
    }

    public LiveData<List<Article>> getAllArticles() {
        allArticles = newsRepository.getAllArticles();
        return allArticles;
    }

    public void insertOrUpdateArticle(Article article) {
        newsRepository.insertArticle(article);
        allArticles = newsRepository.getAllArticles();
    }

    public void deleteArticle(Article article) {
        newsRepository.deleteArticle(article);
    }

    public void refreshArticles() {
        // Optionally, force refresh of data by clearing cache or reloading
        // In this case, just re-fetch data manually from repository
        allArticles = newsRepository.getAllArticles();
    }


}
