package com.example.newsapp.repository;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.model.Article;
import com.example.newsapp.model.NewsResponse;
import com.example.newsapp.network.RetrofitClient;
import com.example.newsapp.service.APIService;

import java.util.List;

/**
 * Repository class responsible for handling news api operations
 */
public class NewsRemoteRepository {

    private static final String TAG = "REMOTE_REPOSITORY";

    // API service instance for making network requests
    private APIService apiService;

    // LiveData to hold the list of news articles for display
    private MutableLiveData<List<Article>> newsListLiveData;

    // LiveData to hold the list of breaking news articles
    private MutableLiveData<List<Article>> breakingNewsListLiveData;

    /**
     * Constructor to initialize the News API service.
     * Creates the APIService instance to make network requests.
     */
    public NewsRemoteRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
    }

    /**
     * Fetches the breaking news for a specific country using an API key.
     * It returns a LiveData object that can be observed for updates.
     *
     * @param country The country code to filter the breaking news.
     * @param apiKey The API key for authentication.
     * @return MutableLiveData object containing the breaking news articles.
     */
    public MutableLiveData<List<Article>> getBreakingNews(String country, String apiKey){
        // If breaking news LiveData is not initialized, initialize it and fetch breaking news
        if(breakingNewsListLiveData == null){
            breakingNewsListLiveData = new MutableLiveData<>();
            getBreakingNewsHelper(country, apiKey);
        }

        // If breaking news data is empty, refresh the LiveData
        if(breakingNewsListLiveData.getValue() != null && breakingNewsListLiveData.getValue().isEmpty()){
            breakingNewsListLiveData = new MutableLiveData<>();
            getBreakingNewsHelper(country, apiKey);
        }

        // Return the breaking news LiveData
        return breakingNewsListLiveData;
    }

    /**
     * Helper method to fetch breaking news articles from the API.
     * It updates the breakingNewsListLiveData with the fetched articles.
     *
     * @param country The country code to filter the breaking news.
     * @param apiKey The API key for authentication.
     */
    private void getBreakingNewsHelper(String country, String apiKey){
        // Make an asynchronous network call to fetch breaking news
        apiService.getHeadlines(country, apiKey).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // If the response is successful, update the LiveData with the articles
                    breakingNewsListLiveData.setValue(response.body().getArticles());
                    Log.d(TAG, "onResponse: get breaking news successfully" + response);
                } else {
                    // If the response is unsuccessful, set LiveData to null
                    // this will update the ui and show a warning: no data available
                    breakingNewsListLiveData.setValue(null);
                    Log.d(TAG, "onResponse: get breaking news failed " + response);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable throwable) {
                // network failure, set the LiveData to null
                breakingNewsListLiveData.setValue(null); // Handle failure case
                Log.d(TAG, "onResponse: get breaking news failed " + throwable.getMessage());
            }
        });
    }

    /**
     * Fetches news articles based on a search query and API key.
     * It ensures that network operations are not repeated unnecessarily.
     *
     * @param query The search query for filtering news articles.
     * @param apiKey The API key for authentication.
     * @return MutableLiveData object containing the search result articles.
     */
    public MutableLiveData<List<Article>> getNews(String query, String apiKey) {
        // Fetch news based on the query using a helper method
        newsListLiveData = new MutableLiveData<>();
        getNewsHelper(query, apiKey);

        // Return the LiveData for the fetched articles
        return newsListLiveData;
    }

    /**
     * Helper method to fetch articles based on a search query.
     * It updates the newsListLiveData with the fetched articles.
     *
     * @param query The search query for filtering news articles.
     * @param apiKey The API key for authentication.
     */
    private void getNewsHelper(String query, String apiKey){
        // Make an asynchronous network call to fetch articles based on the query
        apiService.getNews(query, apiKey).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // If the response is successful, update the LiveData with the articles
                    newsListLiveData.setValue(response.body().getArticles());
                    Log.d(TAG, "onResponse: search news successful" + response);
                } else {
                    // If the response is unsuccessful, set LiveData to null
                    newsListLiveData.setValue(null);
                    Log.d(TAG, "onResponse: search news failed " + response);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // network error: set the LiveData to null
                newsListLiveData.setValue(null); // Handle failure case
                Log.d(TAG, "onResponse: search news failed " + t.getMessage());
            }
        });
    }
}
