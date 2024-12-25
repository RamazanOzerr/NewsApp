package com.example.newsapp.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.model.Article;
import com.example.newsapp.model.NewsResponse;
import com.example.newsapp.repository.NewsRemoteRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {

    private NewsRemoteRepository newsRepository;
    private LiveData<List<Article>> newsListLiveData;

    private static final String TAG = "NEWS_VIEW_MODEL";

    public NewsViewModel() {
        newsRepository = new NewsRemoteRepository();
    }

    public LiveData<List<Article>> getNewsList(String country, String apiKey) {
        newsListLiveData = newsRepository.getBreakingNews(country, apiKey);
        Log.d(TAG, "getNewsList: country: " + country + " " + "api key: " + apiKey);
        return newsListLiveData;
    }

}
