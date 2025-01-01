package com.example.newsapp.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.model.Article;
import com.example.newsapp.model.NewsResponse;
import com.example.newsapp.repository.NewsRemoteRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {

    // Repository instance to fetch news from the remote source
    private NewsRemoteRepository newsRepository;

    // LiveData to hold the list of news articles
    private LiveData<List<Article>> newsListLiveData;

    private static final String TAG = "NEWS_VIEW_MODEL";

    /**
     * Constructor to initialize the repository.
     */
    public NewsViewModel() {
        // Initialize the NewsRemoteRepository for fetching news data
        newsRepository = new NewsRemoteRepository();
    }

    /**
     * Fetches the news list based on country and API key.
     *
     * @param country The country code (e.g., "us" for the United States).
     * @param apiKey  The API key required for authenticating with the news API.
     * @return LiveData containing the list of articles fetched from the repository.
     */
    public LiveData<List<Article>> getNewsList(String country, String apiKey) {
        // Call the repository to fetch breaking news and get the LiveData object
        newsListLiveData = newsRepository.getBreakingNews(country, apiKey);

        // Return the LiveData, so UI can observe it for updates
        return newsListLiveData;
    }
}
