package com.example.newsapp.ui.searchNews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.model.Article;
import com.example.newsapp.repository.NewsRemoteRepository;

import java.util.List;

public class SearchNewsViewModel extends ViewModel {

    // Repository for fetching news articles from the remote API
    private NewsRemoteRepository newsRepository;

    // LiveData object holding the list of news articles to observe in the UI
    private LiveData<List<Article>> newsListLiveData;

    // Constructor: Initializes the NewsRemoteRepository to fetch news articles
    public SearchNewsViewModel() {
        newsRepository = new NewsRemoteRepository();
    }

    // Method to fetch news articles based on the query and API key
    public LiveData<List<Article>> getNewsList(String query, String apiKey) {
        newsListLiveData = newsRepository.getNews(query, apiKey);
        return newsListLiveData;
    }
}