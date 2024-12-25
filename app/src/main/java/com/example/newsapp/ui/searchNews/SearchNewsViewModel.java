package com.example.newsapp.ui.searchNews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.model.Article;
import com.example.newsapp.repository.NewsRemoteRepository;

import java.util.List;

public class SearchNewsViewModel extends ViewModel {

    private NewsRemoteRepository newsRepository;
    private LiveData<List<Article>> newsListLiveData;

    public SearchNewsViewModel() {
        newsRepository = new NewsRemoteRepository();
    }

    public LiveData<List<Article>> getNewsList(String query, String apiKey) {
        newsListLiveData = newsRepository.getNews(query, apiKey);
        return newsListLiveData;
    }
}